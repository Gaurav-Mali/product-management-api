package com.zestindia.productmanagement.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item", indexes = @Index(name = 
"idx_product_id",columnList = "product_id"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private Integer quantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
}
