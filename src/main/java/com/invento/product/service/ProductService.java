package com.invento.product.service;

import java.util.List;

import org.bson.Document;
import org.springframework.data.domain.Sort;

import com.invento.product.dto.ProductDto;

public interface ProductService {

	public List<ProductDto> getProductList(int pageNumber, int pageSize, String field, Sort.Direction sortDirection);

	public ProductDto getProductById(String id);
	
	public List<Document> searchProduct(String keyword, int limit);

	public boolean addProduct(ProductDto dto);
	
	public void updateProduct(ProductDto dto);

	public void deleteProductById(String id);
}
