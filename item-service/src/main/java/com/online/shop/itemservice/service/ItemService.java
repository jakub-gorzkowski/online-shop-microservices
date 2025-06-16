package com.online.shop.itemservice.service;

import com.online.shop.itemservice.domain.dto.ItemResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ItemService {
    Page<ItemResponse> readAllItems(Integer offset, Byte size);
    ItemResponse readItem(UUID id);
}
