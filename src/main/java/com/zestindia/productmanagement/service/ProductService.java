package com.zestindia.productmanagement.service;

import com.zestindia.productmanagement.dto.request.ProductRequest;
import com.zestindia.productmanagement.dto.response.ItemResponse;
import com.zestindia.productmanagement.dto.response.ProductResponse;
import com.zestindia.productmanagement.entity.Product;
import com.zestindia.productmanagement.exception.ResourceNotFoundException;
import com.zestindia.productmanagement.repository.ItemRepository;
import com.zestindia.productmanagement.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;

    // Helper method to convert Product Entity to ProductResponse DTO
    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .createdBy(product.getCreatedBy())
                .createdOn(product.getCreatedOn())
                .modifiedBy(product.getModifiedBy())
                .modifiedOn(product.getModifiedOn())
                .build();
    }

    // CREATE
    public ProductResponse createProduct(ProductRequest request) {
        Product product = Product.builder()
                .productName(request.getProductName())
                .createdBy(request.getCreatedBy())
                .build();

        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    // GET ALL (With Pagination)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(this::mapToProductResponse);
    }

    // GET BY ID
    public ProductResponse getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return mapToProductResponse(product);
    }

    // UPDATE
    public ProductResponse updateProduct(Integer id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        product.setProductName(request.getProductName());
        product.setModifiedBy(request.getCreatedBy()); // Setting the modifier

        Product updatedProduct = productRepository.save(product);
        return mapToProductResponse(updatedProduct);
    }

    // DELETE
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    // GET ITEMS FOR A PRODUCT
    public List<ItemResponse> getProductItems(Integer productId) {
        // Verify product exists first
        productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        return itemRepository.findByProductId(productId).stream()
                .map(item -> ItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProduct().getId())
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }
}