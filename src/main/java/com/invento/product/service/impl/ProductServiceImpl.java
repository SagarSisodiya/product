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

import com.invento.product.dto.ProductDto;
import com.invento.product.exception.ProductNotFoundException;
import com.invento.product.mapper.ProductMapper;
import com.invento.product.model.Product;
import com.invento.product.repository.ProductRepo;
import com.invento.product.service.ProductService;
import com.invento.product.util.Constants;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.search.FieldSearchPath;

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

	@Override
	public List<Product> getProductList(int pageNumber, int pageSize, String field, Sort.Direction sortDirection) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sortDirection, field);
		return productRepo.findAllByDeleted(false, pageable).toList();
	}

	@Override
	public Product getProductById(String id) {

		return productRepo.findByIdAndDeleted(id, false)
				.orElseThrow(() -> new ProductNotFoundException("No product found with id: " + id));
	}
	
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

	@Override
	@Transactional
	public boolean addProduct(ProductDto dto) {

		boolean created = false;
		Product product = productMapper.dtoToProduct(dto);
		product = productRepo.save(product);
		if (Strings.isNotBlank(product.getId())) {
			created = true;
			log.info("Product created. Product Details: {}", product);
		}
		return created;
	}
	
	@Override
	@Transactional
	public boolean updateProduct(ProductDto dto) {

		boolean updated = false;
		Product product = productRepo.findByIdAndDeleted(dto.getId(), false)
				.orElseThrow(() -> new ProductNotFoundException("No product found with id: " + dto.getId()));
		product = productMapper.dtoToProduct(dto);
		productRepo.save(product);
		updated = true;
		return updated;
	}

	@Override
	@Transactional
	public boolean deleteProductById(String id) {

		boolean deleted = false;
		Product product = productRepo.findByIdAndDeleted(id, false)
				.orElseThrow(() -> new ProductNotFoundException("No product found with id: " + id));
		product.setDeleted(true);
		productRepo.save(product);
		deleted = true;
		return deleted;
	}
}
