package com.invento.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.invento.product.model.Brand;

/**
 * Repository for managing {@link com.invento.product.model.Brand}
 */

@Repository
public interface BrandRepo extends MongoRepository<Brand, String> {

	List<Brand> findAllByDeleted(boolean b);
	
	Optional<Brand> findByIdAndDeleted(String id, boolean b);
}
