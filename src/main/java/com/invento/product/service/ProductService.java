package com.invento.product.service;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.data.domain.Sort;

import com.invento.product.dto.ProductDto;
import com.invento.product.model.Product;

public interface ProductService {

	public List<Product> getProductList(int pageNumber, int pageSize, String field, Sort.Direction sortDirection);

	public Optional<Product> getProductById(String id);

	public boolean addProduct(ProductDto dto);

	public boolean deleteProductById(String id);

	public boolean updateProduct(ProductDto dto);
	
	public List<Document> searchProduct(String keyword, int limit);
}
