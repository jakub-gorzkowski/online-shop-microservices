package com.online.shop.itemservice.domain.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRequest {

    @NotBlank
    @Size(min = 2, max = 256, message = "Name should be between 2 and 256 characters long")
    private String name;

    @NotBlank
    @Size(min = 16, max = 8192, message = "Description should be between 16 and 8192 characters long")
    private String description;

    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @NotBlank
    @Size(min = 2, max = 256, message = "Manufacturer name should be between 2 and 256 characters long")
    private String manufacturer;

    @NotBlank
    @Size(min = 2, max = 128, message = "Category should be between 2 and 128 characters long")
    private String category;
}
