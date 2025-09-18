package com.example.inventory.controller;
import com.example.inventory.model.Supplier;
import com.example.inventory.repository.SupplierRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final SupplierRepository repo;

    public SupplierController(SupplierRepository repo) { this.repo = repo; }

    @GetMapping
    public ResponseEntity<List<Supplier>> all() { return ResponseEntity.ok(repo.findAll()); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Supplier> create(@RequestBody Supplier s) {
        return ResponseEntity.status(201).body(repo.save(s));
    }
}
