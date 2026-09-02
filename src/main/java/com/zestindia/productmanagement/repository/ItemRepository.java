package com.zestindia.productmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zestindia.productmanagement.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{
	List<Item> findByProductId(Integer productId);
}
