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
import java.util.Optional;

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

import com.invento.product.dto.ProductDto;
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

	@Value("${mongodb.product.collection}")
	private String productCollection;

	private ProductRepo productRepo;

	private ProductMapper productMapper;

	private MongoCollection<Document> collection;

	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	public ProductServiceImpl(ProductRepo productRep, ProductMapper productMapper, MongoTemplate mongoTemplate) {
		this.productRepo = productRep;
		this.productMapper = productMapper;
		this.collection = mongoTemplate.getCollection("product");
	}

	@Override
	public List<Product> getProductList(int pageNumber, int pageSize, String field, Sort.Direction sortDirection) {
		List<Product> products = new ArrayList<>();
		try {
			Pageable pageable = PageRequest.of(pageNumber, pageSize, sortDirection, field);
			products = productRepo.findAllByDeleted(false, pageable).toList();
		} catch (Exception e) {
			log.error("Could not find products.");
		}
		return products;
	}

	@Override
	public Optional<Product> getProductById(String id) {
		Optional<Product> productOp = Optional.empty();
		try {
			productOp = productRepo.findByIdAndDeleted(id, false);
			if (productOp.isEmpty()) {
				log.error("Product not found with id: {}", id);
			}
		} catch (Exception e) {
			log.error("Error finidng product with id: {}", id);
		}
		return productOp;
	}

	@Override
	public boolean addProduct(ProductDto dto) {
		boolean created = false;
		try {
			Product product = productMapper.dtoToProduct(dto);
			product = productRepo.insert(product);
			if (Strings.isNotBlank(product.getId())) {
				created = true;
				log.info("Product created. Product Details: {}", product);
			}
		} catch (Exception e) {
			log.error("Product creation failed. Error: {}", e.getMessage());
		}
		return created;
	}

	@Override
	public boolean deleteProductById(String id) {
		boolean deleted = false;
		try {
			Optional<Product> productOp = productRepo.findById(id);
			if (productOp.isPresent()) {
				Product product = productOp.get();
				product.setDeleted(true);
				productRepo.save(product);
				deleted = true;
			} else {
				log.error("Product not found with id: {}", id);
			}
		} catch (Exception e) {
			log.error("Error deleting product with id: {}. Error: {}", id, e.getMessage());
		}
		return deleted;
	}

	@Override
	public boolean updateProduct(ProductDto dto) {
		boolean updated = false;
		try {
			if (Strings.isBlank(dto.getId())) {
				throw new Exception("Id is null/empty.");
			}
			Optional<Product> productOp = productRepo.findById(dto.getId());
			if (productOp.isEmpty()) {
				throw new Exception("Product not found.");
			}
			Product product = productOp.get();
			if (product.isDeleted()) {
				throw new Exception("Product was deleted. Please enable before updating.");
			}

			product = productMapper.dtoToProduct(dto);
			productRepo.save(product);
			updated = true;
		} catch (Exception e) {
			log.error("Error updating product. Error: {}", e.getMessage());
		}
		return updated;
	}

	@Override
	public List<Document> searchProduct(String keyword, int limit) {
		
		List<Document> docs = null;
		List<FieldSearchPath> fieldPaths = new ArrayList<>();
		try {
			log.info("Searching movies by keyword {} and limit {}", keyword, limit);
			Constants.SEARCH_PRODUCT_FIELDS
				.stream().forEach(f -> fieldPaths.add(fieldPath(f)));
			Bson searchStage = search(
					text(fieldPaths, Arrays.asList(keyword)),
					searchOptions().index(searchProductIdx));
			Bson projectStage = project(
					fields(excludeId(), include(Constants.INCLUDE_PRODUCT_FIELDS)));
			List<Bson> pipeline = List.of(searchStage, projectStage, limit(limit));
			docs = collection.aggregate(pipeline).into(new ArrayList<>());
			if (docs.isEmpty()) {
				throw new Exception("Product not found with keyword: " + keyword);
			}
		} catch (Exception e) {
			log.error("Error searching product: {}", e.getMessage());
		}
		return docs;
	}
}
