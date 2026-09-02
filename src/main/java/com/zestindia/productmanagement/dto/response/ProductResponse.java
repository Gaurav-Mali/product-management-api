package com.zestindia.productmanagement.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
	private Integer id;
	private String productName;
	private String createdBy;
	private LocalDateTime createdOn;
	private String modifiedBy;
	private LocalDateTime modifiedOn;
}
