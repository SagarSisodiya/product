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
import com.invento.product.model.Product;

@Component
public class TestUtils {
	
	private static final Logger log = LoggerFactory.getLogger(TestUtils.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	public Product getProduct() {
		
		return Product.builder()
				.id("6639dd31e5b7490f597ee80f")
				.category("Cabinet")
				.brand("Corsair")
				.productName("Zeus 2000")
				.spec("Liquid Cooled RGB")
				.deleted(false)
				.imagename("corsaircabzeus.jpg")
				.build();
	}
	
	public List<ProductDto> getProductDtos() {
				
		ProductDto product1 =	
				ProductDto.builder()
				.id("6639dd31e5b7490f597ee80f")
				.category("Cabinet")
				.brand("Corsair")
				.productName("Zeus 2000")
				.spec("Liquid Cooled RGB")
				.imagename("corsaircabzeus.jpg")
				.build();
		
		ProductDto product2 =	
				ProductDto.builder()
				.id("667512965128ae4fdd899203")
				.category("Cpu")
				.brand("Intel")
				.productName("Core i9")
				.spec("16 Core 24MB cache")
				.imagename("inteli9.jpg")
				.build();
		
		ProductDto product3 =	
				ProductDto.builder()
				.id("667512a2b880f56a1f49b0ba")
				.category("Hard Drive")
				.brand("Seagate")
				.productName("Streamer 4000")
				.spec("5 TB")
				.imagename("seagatestreamer.jpg")
				.build();
		
		List<ProductDto> products = Arrays.asList(product1, product2, product3);
		return products;
	}
	
	public List<Product> getProducts() {
		
		Product product1 =	
				Product.builder()
				.id("6639dd31e5b7490f597ee80f")
				.category("Cabinet")
				.brand("Corsair")
				.productName("Zeus 2000")
				.spec("Liquid Cooled RGB")
				.imagename("corsaircabzeus.jpg")
				.build();
		
		Product product2 =	
				Product.builder()
				.id("667512965128ae4fdd899203")
				.category("Cpu")
				.brand("Intel")
				.productName("Core i9")
				.spec("16 Core 24MB cache")
				.imagename("inteli9.jpg")
				.build();
		
		Product product3 =	
				Product.builder()
				.id("667512a2b880f56a1f49b0ba")
				.category("Hard Drive")
				.brand("Seagate")
				.productName("Streamer 4000")
				.spec("5 TB")
				.imagename("seagatestreamer.jpg")
				.build();
		
		List<Product> products = Arrays.asList(product1, product2, product3);
		return products;
	}
	
	public ProductDto getProductDto() {
		
		return ProductDto.builder()
				.id("6639dd31e5b7490f597ee80f")
				.category("Cabinet")
				.brand("Corsair")
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
