package com.invento.product.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.invento.product.model.Product;

/**
 * Repository for managing {@link com.invento.product.model.Product}
 */

@Repository
public interface ProductRepo extends MongoRepository<Product, String>{

	Page<Product> findAllByDeleted(boolean b, Pageable page);
	
	Optional<Product> findByIdAndDeleted(String id, boolean b);
}
