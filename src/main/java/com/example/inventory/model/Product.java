package com.example.inventory.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    private String description;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private com.example.inventory.model.Category category;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private com.example.inventory.model.Supplier supplier;

    @Column(nullable=false)
    private Integer quantity;

    @Column(nullable=false)
    private BigDecimal price;
}