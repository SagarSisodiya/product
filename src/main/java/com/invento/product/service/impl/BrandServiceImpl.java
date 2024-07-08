package com.invento.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.invento.product.dto.BrandDto;
import com.invento.product.exception.BrandNotFoundException;
import com.invento.product.exception.InvalidRequestException;
import com.invento.product.mapper.BrandMapper;
import com.invento.product.model.Brand;
import com.invento.product.repository.BrandRepo;
import com.invento.product.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

	private BrandRepo brandRepo;
	
	private BrandMapper brandMapper;
	
	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	BrandServiceImpl(BrandRepo brandRepo, BrandMapper brandMapper) {
		
		this.brandRepo = brandRepo;
		this.brandMapper = brandMapper;
	}
	
	/**
	 * Get list of Brand details objects where value of deleted property is false
	 * 
	 * @return List of BrandDto
	 * 
	 */
	@Override
	public List<BrandDto> getBrandList() {
		
		List<BrandDto> dtoList = new ArrayList<>();
		List<Brand> brandList = brandRepo.findAllByDeleted(false);
		if(!CollectionUtils.isEmpty(brandList)) {
			dtoList = brandMapper.brandToDto(brandList);
		}
		return dtoList;
	}
	
	/**
	 * Add new brand objects
	 * 
	 * @param dtos: Brand details to be stored
	 * 
	 * throws InvalidRequestException if dtos is null/empty
	 * 
	 */
	@Override
	public void addBrand(List<BrandDto> dtos) {
		
		if(CollectionUtils.isEmpty(dtos)) {
			throw new InvalidRequestException(
				"Dto list is null/empty.");
		}
		List<Brand> brandList = brandMapper.dtoToBrand(dtos);
		brandRepo.saveAll(brandList);
		log.info("Brands saved successfully.");
	}

	/**
	 * Update existing brand object where id matches the provided id in request object
	 * 
	 * @param dto : Request payload containing brand details to be updated
	 * 
	 * throws InvalidRequestException with status code 400 {BAD_REQUEST} and an error message
	 * if request dto object is null
	 * 
	 * throws BrandNotFoundException with status code 404 {NOT_FOUND} and an error message
	 * if object is not found matching the provided id.
	 * 
	 */
	@Override
	public void updateBrand(BrandDto dto) {
		
		if(Objects.isNull(dto)) {
			throw new InvalidRequestException("Dto is null.");
		}
		Brand brand = brandRepo.findByIdAndDeleted(dto.getId(), false).orElseThrow(
			() -> new BrandNotFoundException("Brand not found with id: " + dto.getId()));
		brand = brandMapper.updateDtoToBrand(dto, brand.getCreatedDate(), brand.getCreatedBy());
		brand = brandRepo.save(brand);
		log.info("Brand updated successfully. Brand: {}", brand);
	}

	/**
	 * Delete a brand object
	 * 
	 * @param id :  unique identifier value for specific brand object
	 * 
	 * throws InvalidRequestException with status code 400 {BAD_REQUEST} and an error message
	 * if id is null/empty
	 * 
	 * throws BrandNotFoundException with status code 404 {NOT_FOUND} and an error message
	 * if object is not found matching the provided id.
	 * 
	 */
	@Override
	public void deleteBrand(String id) {
		
		if(Strings.isEmpty(id)) {
			throw new InvalidRequestException("id is null/empty.");
		}
		Brand brand = brandRepo.findByIdAndDeleted(id, false).orElseThrow(
			() -> new BrandNotFoundException("Brand not found with id: " + id));
		brand.setDeleted(true);
		brandRepo.save(brand);
		log.info("Brand deleted with id: {}", id);
	}
}
