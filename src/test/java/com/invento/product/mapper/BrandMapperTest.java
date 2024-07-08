package com.invento.product.mapper;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.invento.product.dto.BrandDto;
import com.invento.product.model.Brand;
import com.invento.product.util.BrandTestUtils;

@ExtendWith(MockitoExtension.class)
public class BrandMapperTest {

	@InjectMocks
	private BrandMapper brandMapper;
	
	@InjectMocks
	private BrandTestUtils testUtils;
	
	@Test
	public void testDtoToBrand() {
		
		BrandDto dto = testUtils.getBrandDto();
		Brand brand  = brandMapper.dtoToBrand(dto);
		
		Assertions.assertThat(brand.getId()).isEqualTo(dto.getId());
		Assertions.assertThat(brand.getName()).isEqualTo(dto.getName());
	}
	
	@Test
	public void testDtoListToBrandList() {
		
		List<BrandDto> dto = testUtils.getBrandDtos();
		List<Brand> brands = brandMapper.dtoToBrand(dto);
		
		Assertions.assertThat(brands.get(0).getId()).isEqualTo(dto.get(0).getId());
		Assertions.assertThat(brands.get(0).getName()).isEqualTo(dto.get(0).getName());
	}
	
	@Test
	public void testBrandListToDtoList() {
		
		List<Brand> brands = testUtils.getBrands();
		List<BrandDto> dto = brandMapper.brandToDto(brands);
		
		Assertions.assertThat(dto.get(0).getId()).isEqualTo(brands.get(0).getId());
		Assertions.assertThat(dto.get(0).getName()).isEqualTo(brands.get(0).getName());
	}
}
