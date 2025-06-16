package com.online.shop.itemservice.domain.mapper;

import com.online.shop.itemservice.domain.dto.ItemRequest;
import com.online.shop.itemservice.domain.dto.ItemResponse;
import com.online.shop.itemservice.domain.entity.Item;

public class ItemMapper {

    public static ItemResponse mapToResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .manufacturer(item.getManufacturer())
                .category(item.getCategory())
                .price(item.getPrice())
                .createdAt(item.getCreatedAt())
                .build();
    }

    public static Item mapFromRequest(ItemRequest request) {
        return Item.builder()
                .name(request.getName())
                .description(request.getDescription())
                .manufacturer(request.getManufacturer())
                .category(request.getCategory().toUpperCase())
                .price(request.getPrice())
                .build();
    }
}
