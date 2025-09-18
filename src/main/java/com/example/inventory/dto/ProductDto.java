package com.example.inventory.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDto {
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @Size(max = 2000)
    private String description;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long supplierId;

    @NotNull
    @Min(0)
    private Integer quantity;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;
}