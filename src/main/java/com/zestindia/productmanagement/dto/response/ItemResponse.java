package com.zestindia.productmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
	private Integer id;
	private Integer productId;
	private Integer quantity;
	
}
