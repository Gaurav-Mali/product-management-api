package com.zestindia.productmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zestindia.productmanagement.entity.Product;

public interface ProductRepository extends
JpaRepository<Product, Integer>{

}
