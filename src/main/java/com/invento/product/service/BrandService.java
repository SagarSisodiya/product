package com.invento.product.service;

import java.util.List;

import com.invento.product.dto.BrandDto;

public interface BrandService {
	
	public List<BrandDto> getBrandList();

	public void addBrand(List<BrandDto> dtos);
	
	public void updateBrand(BrandDto dto);
	
	public void deleteBrand(String id);
}
