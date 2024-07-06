package com.invento.product.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invento.product.dto.ProductDto;
import com.invento.product.enums.Brand;
import com.invento.product.enums.ProductCategory;
import com.invento.product.model.Product;

@Component
public class TestUtils {
	
	private static final Logger log = LoggerFactory.getLogger(TestUtils.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	public Product getProduct() {
		
		return Product.builder()
				.id("6639dd31e5b7490f597ee80f")
				.category(ProductCategory.CABINETS)
				.brand(Brand.CORSAIR)
				.productName("Zeus 2000")
				.spec("Liquid Cooled RGB")
				.deleted(false)
				.imagename("corsaircabzeus.jpg")
				.build();
	}
	
	public List<Product> getProducts() {
				
		Product product1 =	
				Product.builder()
				.id("6639dd31e5b7490f597ee80f")
				.category(ProductCategory.CABINETS)
				.brand(Brand.CORSAIR)
				.productName("Zeus 2000")
				.spec("Liquid Cooled RGB")
				.deleted(false)
				.imagename("corsaircabzeus.jpg")
				.build();
		
		Product product2 =	
				Product.builder()
				.id("667512965128ae4fdd899203")
				.category(ProductCategory.CPU)
				.brand(Brand.INTEL)
				.productName("Core i9")
				.spec("16 Core 24MB cache")
				.deleted(false)
				.imagename("inteli9.jpg")
				.build();
		
		Product product3 =	
				Product.builder()
				.id("667512a2b880f56a1f49b0ba")
				.category(ProductCategory.HARD_DRIVE)
				.brand(Brand.SEAGATE)
				.productName("Streamer 4000")
				.spec("5 TB")
				.deleted(false)
				.imagename("seagatestreamer.jpg")
				.build();
		
		List<Product> products = Arrays.asList(product1, product2, product3);
		return products;
	}
	
	public ProductDto getProductDto() {
		
		return ProductDto.builder()
				.id("6639dd31e5b7490f597ee80f")
				.category(ProductCategory.CABINETS)
				.brand(Brand.CORSAIR)
				.productName("Zeus 2000")
				.spec("Liquid Cooled RGB")
				.imagename("corsaircabzeus.jpg")
				.build();
	}
	
	public List<Document> getDocuments() {
		
		List<Document> docs = new ArrayList<>();
		Document doc = null;
		try {
			doc = Document.parse(mapper.writeValueAsString(getProduct()));
			docs.add(doc);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return docs;
	}
}
