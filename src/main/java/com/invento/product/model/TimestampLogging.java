package com.invento.product.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TimestampLogging {

	@CreatedDate
	private LocalDateTime createdDate;

	@CreatedBy
	private String createdBy;

	@LastModifiedDate
	private LocalDateTime updatedDate;

	@LastModifiedBy
	private String updatedBy;
}
