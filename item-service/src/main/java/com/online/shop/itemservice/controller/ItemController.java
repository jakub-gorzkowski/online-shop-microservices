package com.online.shop.itemservice.controller;

import com.online.shop.itemservice.domain.dto.DetailedItemResponse;
import com.online.shop.itemservice.domain.dto.ItemRequest;
import com.online.shop.itemservice.domain.dto.ItemResponse;
import com.online.shop.itemservice.service.ItemService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<Page<ItemResponse>> getAllItems(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) @Max(127) Byte size
    ) {
        Page<ItemResponse> items = itemService.readAllItems(offset, size);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DetailedItemResponse> getItem(@PathVariable UUID id) {
        return new ResponseEntity<>(itemService.readItem(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DetailedItemResponse> postItem(@RequestBody ItemRequest request) {
        DetailedItemResponse savedItem = itemService.saveItem(request);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<DetailedItemResponse> updateItem(
            @PathVariable UUID id,
            @RequestBody ItemRequest request
    ) {
        DetailedItemResponse updatedItem = itemService.updateItem(id, request);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<DetailedItemResponse> partialUpdateItem(
            @PathVariable UUID id,
            @RequestBody ItemRequest request
    ) {
        DetailedItemResponse updatedItem = itemService.partialUpdateItem(id, request);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }
}
