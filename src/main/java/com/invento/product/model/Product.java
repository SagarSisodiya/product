package com.invento.product.model;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.invento.product.enums.Brand;
import com.invento.product.enums.ProductCategory;

public class Product extends TimestampLogging {

	@Id
	private String id;

	private ProductCategory category;

	private Brand brand;
	
	private String productName;

	private String spec;
	
	@JsonIgnore
	private boolean deleted;
	
	private String imagename;

	public Product() {
		super();
	}

	public Product(ProductCategory category, Brand brand, String spec, boolean deleted, String imagename,
			MetaData metadata) {
		super();
		this.category = category;
		this.brand = brand;
		this.spec = spec;
		this.deleted = deleted;
		this.imagename = imagename;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}
}
