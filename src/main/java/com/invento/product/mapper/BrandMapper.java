package com.invento.product.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import com.invento.product.dto.BrandDto;
import com.invento.product.model.Brand;

@Component
public class BrandMapper {
	
	public Brand dtoToBrand(BrandDto dto) {
		
		return Brand.builder()
				.id(dto.getId())
				.name(dto.getName())
				.deleted(false)				
				.build();
	}

	public List<Brand> dtoToBrand(List<BrandDto> dtos) {
		
		List<Brand> brandList = new ArrayList<>();
		brandList = dtos.stream()
			.filter(d -> Strings.isNotEmpty(d.getName()))
			.map(d -> {
				return Brand.builder()
						.name(d.getName())
						.deleted(false)
						.build();})
			.collect(Collectors.toList());
		return brandList;
	}
	
	public List<BrandDto> brandToDto(List<Brand> brands) {
		
		List<BrandDto> dtoList = new ArrayList<>();
		dtoList = brands.stream()
			.map(b -> {
				return BrandDto.builder()
						.id(b.getId())
						.name(b.getName())
						.build();
			})
			.collect(Collectors.toList());
		return dtoList;
	}
}
