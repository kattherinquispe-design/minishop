package com.tecsup.minishop.controller;

import com.tecsup.minishop.dto.ProductRequest;
import com.tecsup.minishop.model.Product;
import com.tecsup.minishop.repository.ProductRepository;
import com.tecsup.minishop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.tecsup.minishop.repository.ProductRepository;
class ProductControllerDtoTest {

    @Test
    void save_conDto_creaProductoSinIdYResponde201() {
        // Arrange
        ProductRepository repository = mock(ProductRepository.class);
        when(repository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));
        ProductService service = new ProductService(repository);
        ProductController controller = new ProductController(service);
        ProductRequest request = new ProductRequest("Laptop", 2500.0, 10);

        // Act
        ResponseEntity<Product> response = controller.save(request);

        // Assert
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(repository).save(captor.capture());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(captor.getValue().getId());
        assertEquals("Laptop", captor.getValue().getName());
        assertEquals(2500.0, captor.getValue().getPrice());
        assertEquals(10, captor.getValue().getStock());
    }
}