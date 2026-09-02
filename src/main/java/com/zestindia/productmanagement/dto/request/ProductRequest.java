package com.zestindia.productmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductRequest {
	@NotBlank(message = "Product name cannot be empty")
	private String productName;
	
	@NotBlank(message = "Creator name is required")
	private String createdBy;
}
