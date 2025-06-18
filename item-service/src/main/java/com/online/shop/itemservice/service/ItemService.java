package com.online.shop.itemservice.service;

import com.online.shop.itemservice.domain.dto.DetailedItemResponse;
import com.online.shop.itemservice.domain.dto.ItemRequest;
import com.online.shop.itemservice.domain.dto.ItemResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ItemService {
    Page<ItemResponse> readAllItems(Integer offset, Byte size);
    DetailedItemResponse readItem(UUID id);
    DetailedItemResponse saveItem(ItemRequest request);
    DetailedItemResponse updateItem(UUID id, ItemRequest request);
    DetailedItemResponse partialUpdateItem(UUID id, ItemRequest request);
}
