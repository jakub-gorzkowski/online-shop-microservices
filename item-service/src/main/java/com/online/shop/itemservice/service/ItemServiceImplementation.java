package com.online.shop.itemservice.service;

import com.online.shop.itemservice.domain.dto.DetailedItemResponse;
import com.online.shop.itemservice.domain.dto.ItemRequest;
import com.online.shop.itemservice.domain.dto.ItemResponse;
import com.online.shop.itemservice.domain.entity.Item;
import com.online.shop.itemservice.domain.mapper.ItemMapper;
import com.online.shop.itemservice.exception.throwable.ItemNotFoundException;
import com.online.shop.itemservice.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImplementation implements ItemService {

    private final ItemRepository itemRepository;

    /**
     * @param offset Site number
     * @param size Items per site
     * @return Paginated list of all items
     */
    @Override
    public Page<ItemResponse> readAllItems(Integer offset, Byte size) {
        return itemRepository.findAll(PageRequest.of(offset, size))
                .map(ItemMapper::mapToResponse);
    }

    /**
     * @param id Item UUID
     * @return Found item
     */
    @Override
    @SneakyThrows
    public DetailedItemResponse readItem(UUID id) {
        Item foundItem = itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        return ItemMapper.mapToDetailedResponse(foundItem);
    }

    /**
     * @param request Request with item data
     * @return Saved item
     */
    @Override
    public DetailedItemResponse saveItem(ItemRequest request) {
        Item item = ItemMapper.mapFromRequest(request);
        item.setCreatedAt(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        log.info("Saved {} under id {}", savedItem.getName(), savedItem.getId());
        return ItemMapper.mapToDetailedResponse(savedItem);
    }

    /**
     * @param id Item UUID
     * @param request Request with updated item data
     * @return Updated item
     */
    @Override
    public DetailedItemResponse updateItem(UUID id, ItemRequest request) {
        Item item = itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setManufacturer(request.getManufacturer());
        item.setCategory(request.getCategory().toUpperCase());

        Item updatedItem = itemRepository.save(item);
        return ItemMapper.mapToDetailedResponse(updatedItem);
    }

    /**
     * @param id Item UUID
     * @param request Request with partially updated item data
     * @return Updated item
     */
    @Override
    public DetailedItemResponse partialUpdateItem(UUID id, ItemRequest request) {
        Item item = itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        Optional.ofNullable(request.getName()).ifPresent(item::setName);
        Optional.ofNullable(request.getDescription()).ifPresent(item::setDescription);
        Optional.ofNullable(request.getPrice()).ifPresent(item::setPrice);
        Optional.ofNullable(item.getManufacturer()).ifPresent(item::setManufacturer);
        Optional.ofNullable(request.getCategory()).ifPresent(category -> item.setCategory(category.toUpperCase()));

        Item updatedItem = itemRepository.save(item);
        return ItemMapper.mapToDetailedResponse(updatedItem);
    }

    /**
     * @param id Item UUID
     */
    @Override
    public void deleteClient(UUID id) {
        itemRepository.deleteById(id);
        log.info("Item with id {} has been deleted", id);
    }
}
