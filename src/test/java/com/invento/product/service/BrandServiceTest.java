package com.invento.product.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.invento.product.dto.BrandDto;
import com.invento.product.exception.InvalidRequestException;
import com.invento.product.mapper.BrandMapper;
import com.invento.product.model.Brand;
import com.invento.product.repository.BrandRepo;
import com.invento.product.service.impl.BrandServiceImpl;
import com.invento.product.util.BrandTestUtils;
import com.mongodb.client.MongoCollection;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

	@Mock
	private BrandRepo brandRepo;
	
	@Mock
	private MongoCollection<Document> collection;
	
	@Mock
	private MongoTemplate mongoTemplate;
	
	@Mock
	private BrandMapper brandMapper;
	
	@InjectMocks
	private BrandServiceImpl brandService;
	
	@InjectMocks
	private BrandTestUtils testUtils;
	
	@Test
	public void testGetBrandList() {
		
		when(brandRepo.findAllByDeleted(Mockito.anyBoolean()))
			.thenReturn(testUtils.getBrands());
		when(brandMapper.brandToDto(Mockito.anyList()))
			.thenReturn(testUtils.getBrandDtos());
		
		List<BrandDto> dtoList = brandService.getBrandList();
		
		Assertions.assertThat(dtoList.size()).isGreaterThan(0);
	}
	
	@Test
	public void testGetBrandList_notFound() {
		
		when(brandRepo.findAllByDeleted(Mockito.anyBoolean()))
			.thenReturn(new ArrayList<>());
		
		List<BrandDto> dtoList = brandService.getBrandList();
		
		Assertions.assertThat(dtoList.size()).isEqualTo(0);
	}
	
	@Test
	public void testAddBrand() {
		
		when(brandMapper.dtoToBrand(Mockito.anyList()))
			.thenReturn(testUtils.getBrands());
		when(brandRepo.saveAll(Mockito.anyList()))
			.thenReturn(testUtils.getBrands());
		
		brandService.addBrand(testUtils.getBrandDtos());
		
		verify(brandMapper, times(1)).dtoToBrand(Mockito.anyList());
		verify(brandRepo, times(1)).saveAll(Mockito.anyList());
	}
	
	@Test
	public void testAddBrand_invalidRequest() {
		
		Exception ex = assertThrows(InvalidRequestException.class, 
				() -> brandService.addBrand(new ArrayList<>()));
		
		Assertions.assertThat(ex.getMessage()).isEqualTo("Dto list is null/empty.");
	}
	
	@Test
	public void testUpdateBrand() {
		
		Brand brand = testUtils.getBrand();
		
		when(brandRepo.findByIdAndDeleted(Mockito.any(), Mockito.anyBoolean()))
			.thenReturn(Optional.ofNullable(brand));
		when(brandMapper.updateDtoToBrand(Mockito.any(BrandDto.class), Mockito.any(), Mockito.any()))
			.thenReturn(brand);
		when(brandRepo.save(Mockito.any())).thenReturn(brand);
		
		brandService.updateBrand(testUtils.getBrandDto());
		
		verify(brandRepo, times(1)).findByIdAndDeleted(
			Mockito.anyString(), Mockito.anyBoolean());
		verify(brandMapper, times(1)).updateDtoToBrand(Mockito.any(BrandDto.class), 
			Mockito.any(), Mockito.any());
		verify(brandRepo, times(1)).save(Mockito.any());
	}
	
	@Test
	public void testUpdateBrand_invalidRequest() {
		
		Exception ex = assertThrows(InvalidRequestException.class,
				() -> brandService.updateBrand(null));
		
		Assertions.assertThat(ex.getMessage()).isEqualTo("Dto is null.");
	}
	
	@Test
	public void testDeleteBrand() {
		
		Brand brand = testUtils.getBrand();
		
		when(brandRepo.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean()))
			.thenReturn(Optional.ofNullable(brand));
		when(brandRepo.save(Mockito.any())).thenReturn(brand);
		
		brandService.deleteBrand("668bd9b802e26f7521c749fe");
		
		verify(brandRepo, times(1)).findByIdAndDeleted(
			Mockito.anyString(), Mockito.anyBoolean());
		verify(brandRepo, times(1)).save(Mockito.any());
	}
	
	@Test
	public void testDeleteBrand_invalidRequest() {
		
		Exception ex = assertThrows(InvalidRequestException.class,
				() -> brandService.deleteBrand(null));
		
		Assertions.assertThat(ex.getMessage()).isEqualTo("id is null/empty.");
	}
}
