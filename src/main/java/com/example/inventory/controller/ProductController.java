package com.example.inventory.controller;
import com.example.inventory.dto.ProductDto;
import com.example.inventory.model.Product;
import com.example.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final InventoryService inventoryService;

    public ProductController(InventoryService inventoryService) { this.inventoryService = inventoryService; }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> create(@Valid @RequestBody ProductDto dto) {
        Product p = inventoryService.createProduct(dto);
        return ResponseEntity.status(201).body(p);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getProduct(id));
    }

    @GetMapping
    public ResponseEntity<List<Product>> search(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(inventoryService.searchProducts(q));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody ProductDto dto) {
        return ResponseEntity.ok(inventoryService.updateProduct(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inventoryService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
