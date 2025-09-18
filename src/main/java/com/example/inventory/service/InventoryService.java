package com.example.inventory.service;

import com.example.inventory.dto.ProductDto;
import com.example.inventory.exception.ResourceNotFoundException;
import com.example.inventory.model.Category;
import com.example.inventory.model.Product;
import com.example.inventory.model.Supplier;
import com.example.inventory.repository.CategoryRepository;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.math.BigDecimal;

@Service
public class InventoryService {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final SupplierRepository supplierRepo;
    private final ValidationService validationService;
    private final Logger log = LoggerFactory.getLogger(InventoryService.class);

    public InventoryService(ProductRepository productRepo, CategoryRepository categoryRepo,
                            SupplierRepository supplierRepo, ValidationService validationService) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.supplierRepo = supplierRepo;
        this.validationService = validationService;
    }

    public Product createProduct(ProductDto dto) {
        com.example.inventory.model.Category cat = categoryRepo.findById(dto.getCategoryId()).orElseThrow();
        com.example.inventory.model.Supplier sup = supplierRepo.findById(dto.getSupplierId()).orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        Product p = new Product();
        p.setName(validationService.sanitize(dto.getName()));
        p.setDescription(validationService.sanitize(dto.getDescription()));
        p.setCategory(cat);
        p.setSupplier(sup);
        p.setQuantity(dto.getQuantity());
        p.setPrice(dto.getPrice() == null ? BigDecimal.ZERO : dto.getPrice());

        Product saved = productRepo.save(p);
        log.info("Created product {}", saved.getId());
        return (Product) saved;
    }

    public Product updateProduct(Long id, ProductDto dto) {
        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (dto.getName() != null) existing.setName(validationService.sanitize(dto.getName()));
        if (dto.getDescription() != null) existing.setDescription(validationService.sanitize(dto.getDescription()));
        if (dto.getCategoryId() != null) {
            Category cat = categoryRepo.findById(dto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            existing.setCategory(cat);
        }
        if (dto.getSupplierId() != null) {
            Supplier sup = supplierRepo.findById(dto.getSupplierId()).orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
            existing.setSupplier(sup);
        }
        if (dto.getQuantity() != null) existing.setQuantity(dto.getQuantity());
        if (dto.getPrice() != null) existing.setPrice(dto.getPrice());

        Product saved = productRepo.save(existing);
        log.info("Updated product {}", saved.getId());
        return saved;
    }

    public void deleteProduct(Long id) {
        if (!productRepo.existsById(id)) throw new ResourceNotFoundException("Product not found");
        productRepo.deleteById(id);
    }

    public Product getProduct(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public List<Product> searchProducts(String q) {
        if (q == null || q.isBlank()) return productRepo.findAll();
        String sanitized = validationService.sanitize(q);
        return productRepo.findByNameContainingIgnoreCase(sanitized);
    }
}
