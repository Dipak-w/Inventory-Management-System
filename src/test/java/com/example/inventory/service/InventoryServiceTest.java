package com.example.inventory.service;
import com.example.inventory.exception.ResourceNotFoundException;
import com.example.inventory.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {
    @Mock
    com.example.inventory.repository.ProductRepository productRepo;
    @Mock
    com.example.inventory.repository.CategoryRepository categoryRepo;
    @Mock
    com.example.inventory.repository.SupplierRepository supplierRepo;
    @Mock
    com.example.inventory.service.ValidationService validationService;
    com.example.inventory.service.InventoryService sut;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        sut = new com.example.inventory.service.InventoryService(productRepo, categoryRepo, supplierRepo, validationService);
    }

    @Test
    void createProduct_shouldThrowWhenCategoryMissing() {
        when(categoryRepo.findById(1L)).thenReturn(Optional.empty());
        var dto = new com.example.inventory.dto.ProductDto();
        dto.setCategoryId(1L);
        dto.setSupplierId(1L);
        dto.setName("X");
        dto.setQuantity(1);
        dto.setPrice(BigDecimal.ONE);

        assertThrows(ResourceNotFoundException.class, () -> sut.createProduct(dto));
    }

    @Test
    void createProduct_success() {
        com.example.inventory.model.Category c = new com.example.inventory.model.Category(1L, "cat");
        com.example.inventory.model.Supplier s = new com.example.inventory.model.Supplier(1L, "sup", "a@b.c");
        when(categoryRepo.findById(1L)).thenReturn(Optional.of(c));
        when(supplierRepo.findById(1L)).thenReturn(Optional.of(s));
        when(validationService.sanitize(any())).thenAnswer(i -> i.getArgument(0));
        when(productRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        var dto = new com.example.inventory.dto.ProductDto();
        dto.setCategoryId(1L); dto.setSupplierId(1L);
        dto.setName("Name"); dto.setQuantity(2); dto.setPrice(BigDecimal.valueOf(10));

        Product res = sut.createProduct(dto);
        assertNotNull(res);
        assertEquals("Name", res.getName());
        verify(productRepo).save(any());
    }
}
