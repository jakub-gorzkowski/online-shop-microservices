package com.online.shop.itemservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedItemResponse {

    private UUID id;

    private String name;

    private String description;

    private BigDecimal price;

    private String manufacturer;

    private String category;

    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;
}
