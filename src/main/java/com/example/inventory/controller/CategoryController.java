package com.example.inventory.controller;
import com.example.inventory.model.Category;
import com.example.inventory.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryRepository repo;

    public CategoryController(CategoryRepository repo) { this.repo = repo; }

    @GetMapping
    public ResponseEntity<List<Category>> all() { return ResponseEntity.ok(repo.findAll()); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> create(@Valid @RequestBody Category category) {
        return ResponseEntity.status(201).body(repo.save(category));
    }
}
