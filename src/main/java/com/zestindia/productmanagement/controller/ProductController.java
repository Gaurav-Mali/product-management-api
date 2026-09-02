package com.zestindia.productmanagement.controller;

import com.zestindia.productmanagement.dto.request.ProductRequest;
import com.zestindia.productmanagement.dto.response.ItemResponse;
import com.zestindia.productmanagement.dto.response.ProductResponse;
import com.zestindia.productmanagement.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 1. CREATE PRODUCT (Includes @Valid for Phase 9 Validation)
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        return new ResponseEntity<>(productService.createProduct(request), HttpStatus.CREATED);
    }

    // 2. GET ALL PRODUCTS (Includes Pagination)
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    // 3. GET PRODUCT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // 4. UPDATE PRODUCT
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Integer id,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    // 5. DELETE PRODUCT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // 6. GET ITEMS FOR A PRODUCT
    @GetMapping("/{id}/items")
    public ResponseEntity<List<ItemResponse>> getProductItems(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductItems(id));
    }
}