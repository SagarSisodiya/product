package com.invento.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.invento.product.model.Category;

/**
 * Repository for managing {@link com.invento.product.model.Category}
 */

@Repository
public interface CategoryRepo extends MongoRepository<Category, String> {

	List<Category> findAllByDeleted(boolean b);
	
	Optional<Category> findByIdAndDeleted(String id, boolean b);
}
