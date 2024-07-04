package com.invento.product.service;

import java.util.List;

import org.bson.Document;
import org.springframework.data.domain.Sort;

import com.invento.product.dto.ProductDto;
import com.invento.product.model.Product;

public interface ProductService {

	public List<Product> getProductList(int pageNumber, int pageSize, String field, Sort.Direction sortDirection);

	public Product getProductById(String id);
	
	public List<Document> searchProduct(String keyword, int limit);

	public boolean addProduct(ProductDto dto);
	
	public void updateProduct(ProductDto dto);

	public void deleteProductById(String id);
}
