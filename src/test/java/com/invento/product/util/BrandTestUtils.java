package com.invento.product.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.invento.product.dto.BrandDto;
import com.invento.product.model.Brand;

@Component
public class BrandTestUtils {
	
	public Brand getBrand() {
		
		return Brand.builder()
				.id("668bd9b802e26f7521c749fe")
				.name("Intel")
				.deleted(false)
				.build();
	}
	
	public List<Brand> getBrands() {
		
		Brand brand1 = Brand.builder()
				.id("668bd9b802e26f7521c749fe")
				.name("Intel")
				.deleted(false)
				.build();
		
		Brand brand2 = Brand.builder()
				.id("668bd9b802e26f7521c749ff")
				.name("Kingston")
				.deleted(false)
				.build();
		
		Brand brand3 = Brand.builder()
				.id("668bde7fb9239a19e9f58aec")
				.name("Corsair")
				.deleted(false)
				.build();
		
		return Arrays.asList(brand1, brand2, brand3);
	}
	
	public BrandDto getBrandDto() {
		
		return BrandDto.builder()
				.id("668bd9b802e26f7521c749fe")
				.name("Intel")
				.build();
	}
	
	public List<BrandDto> getBrandDtos() {
		
		BrandDto brand1 = BrandDto.builder()
				.id("668bd9b802e26f7521c749fe")
				.name("Intel")
				.build();
		
		BrandDto brand2 = BrandDto.builder()
				.id("668bd9b802e26f7521c749ff")
				.name("Kingston")
				.build();
		
		BrandDto brand3 = BrandDto.builder()
				.id("668bde7fb9239a19e9f58aec")
				.name("Corsair")
				.build();
		
		return Arrays.asList(brand1, brand2, brand3);
	}
}
