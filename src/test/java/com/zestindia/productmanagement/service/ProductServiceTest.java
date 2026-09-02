package com.zestindia.productmanagement.service;

import com.zestindia.productmanagement.dto.response.ProductResponse;
import com.zestindia.productmanagement.entity.Product;
import com.zestindia.productmanagement.exception.ResourceNotFoundException;
import com.zestindia.productmanagement.repository.ItemRepository;
import com.zestindia.productmanagement.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .id(1)
                .productName("Test Laptop")
                .createdBy("Admin")
                .createdOn(LocalDateTime.now())
                .build();
    }

    @Test
    void getProductById_Success() {
        // Arrange: Tell the mock database what to return
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));

        // Act: Call the service
        ProductResponse response = productService.getProductById(1);

        // Assert: Verify the results
        assertNotNull(response);
        assertEquals("Test Laptop", response.getProductName());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void getProductById_ThrowsResourceNotFoundException() {
        // Arrange: Tell the mock database to return empty
        when(productRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert: Verify it throws your custom exception
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductById(99);
        });

        verify(productRepository, times(1)).findById(99);
    }
}