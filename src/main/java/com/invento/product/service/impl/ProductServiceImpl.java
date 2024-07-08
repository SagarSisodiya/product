package com.invento.product.service.impl;

import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.search;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.search.SearchOperator.text;
import static com.mongodb.client.model.search.SearchOptions.searchOptions;
import static com.mongodb.client.model.search.SearchPath.fieldPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.util.Strings;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.invento.product.dto.ProductDto;
import com.invento.product.exception.InvalidRequestException;
import com.invento.product.exception.ProductNotFoundException;
import com.invento.product.mapper.ProductMapper;
import com.invento.product.model.Product;
import com.invento.product.repository.ProductRepo;
import com.invento.product.service.ProductService;
import com.invento.product.util.Constants;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.search.FieldSearchPath;

/**
 * Service implementation for managing {@link com.invento.product.model.Product}
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Value("${mongodb.product.searchIndex}")
	private String searchProductIdx;

	private ProductRepo productRepo;

	private ProductMapper productMapper;

	private MongoCollection<Document> collection;

	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	public ProductServiceImpl(ProductRepo productRep, ProductMapper productMapper, MongoTemplate mongoTemplate) {
		
		this.productRepo = productRep;
		this.productMapper = productMapper;
		this.collection = mongoTemplate.getCollection(Constants.PRODUCT_COLLECTION);
	}

	/**
	 * Get list of products where 'deleted' field value is false
	 * 
	 * @param pageNumber : Specific page number in paginated response of all products
	 * @param pageSize : Number of product objects in each page
	 * @param field : Field used for sorting in product object
	 * @param sortDirection {@value ASC/DESC} : Sorting order - {ASC} for ascending and {DESC} for descending
	 * 
	 * @return List of products
	 * 
	 */
	@Override
	public List<ProductDto> getProductList(int pageNumber, int pageSize, String field, Sort.Direction sortDirection) {

		List<ProductDto> dtos = new ArrayList<>();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sortDirection, field);
		List<Product> products = productRepo.findAllByDeleted(false, pageable).toList();
		if(!CollectionUtils.isEmpty(products)) {
			dtos = productMapper.productToDto(products);
		}
		return dtos;
	}

	/**
	 * Get product by id where 'deleted' field value is false
	 * 
	 * @param id : unique identifier value for specific product object
	 *  
	 * @return Product object or throws ProductNotFoundException if object is not found matching the provided id.
	 */
	@Override
	public ProductDto getProductById(String id) {

		Product product = productRepo.findByIdAndDeleted(id, false)
				.orElseThrow(() -> new ProductNotFoundException("No product found with id: " + id));
		return productMapper.productToDto(product);
	}
	
	/**
	 * Search products in mongodb database
	 * 
	 * @param keyword : String values to be search over the indexed fields in mongodb
	 * 
	 * @param limit : Number of objects to be fetched
	 * 
	 * @return List of product documents or throws ProductNotFoundException if no document matches
	 *         the provided keyword/s. 
	 *  
	 */
	@Override
	public List<Document> searchProduct(String keyword, int limit) {

		List<Document> docs = null;
		List<FieldSearchPath> fieldPaths = new ArrayList<>();
		log.info("Searching movies by keyword {} and limit {}", keyword, limit);
		Constants.SEARCH_PRODUCT_FIELDS.stream().forEach(f -> fieldPaths.add(fieldPath(f)));
		Bson searchStage = search(text(fieldPaths, Arrays.asList(keyword)), searchOptions().index(searchProductIdx));
		Bson projectStage = project(fields(excludeId(), include(Constants.INCLUDE_PRODUCT_FIELDS)));
		List<Bson> pipeline = List.of(searchStage, projectStage, limit(limit));
		docs = collection.aggregate(pipeline).into(new ArrayList<>());

		if (docs.isEmpty()) {
			throw new ProductNotFoundException("No product found");
		}
		return docs;
	}
	
	/**
	 * Add new product
	 * 
	 * @param dto: Request object containing product details to be stored
	 * 
	 * @return true if product is saved successfully or else returns false.
	 */
	@Override
	@Transactional
	public boolean addProduct(ProductDto dto) {

		boolean created = false;
		
		if(Objects.isNull(dto)) {
			throw new InvalidRequestException("Request dto is null");
		}
		Product product = productMapper.dtoToProduct(dto);
		product = productRepo.save(product);
		if (Strings.isNotBlank(product.getId())) {
			created = true;
			log.info("Product created. Product Details: {}", product);
		}
		return created;
	}
	
	/**
	 * Update existing product where id matches the provided id in dto.
	 * 
	 * @param dto : Request object containing product details to be updated.
	 * 
	 * @return true if product details updated successfully else returns false,
	 *         or throws ProductNotFoundException if no product matches the provided id in dto.
	 */
	@Override
	@Transactional
	public void updateProduct(ProductDto dto) {

		Product product = productRepo.findByIdAndDeleted(dto.getId(), false)
				.orElseThrow(() -> new ProductNotFoundException("No product found with id: " + dto.getId()));
		product = productMapper.updateDtoToProduct(dto, product.getCreatedDate(), product.getCreatedBy());
		productRepo.save(product);
		log.info("Product with id {} updated successfully.");
	}
	
	/**
	 * Soft delete product by setting 'deleted' field value of product to true, that matches the provided id.
	 * 
	 * @param id : unique identifier value for specific product object
	 * 
	 * @return true if the product is delete successfully else returns false, 
	 *         or throw ProductNotFoundException if no product matches the provided id.
	 */
	@Override
	@Transactional
	public void deleteProductById(String id) {

		Product product = productRepo.findByIdAndDeleted(id, false)
				.orElseThrow(() -> new ProductNotFoundException("No product found with id: " + id));
		product.setDeleted(true);
		productRepo.save(product);
		log.info("Product with id {} deleted successfully.");
	}
}
