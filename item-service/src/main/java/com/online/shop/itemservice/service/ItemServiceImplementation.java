package com.online.shop.itemservice.service;

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
    public ItemResponse readItem(UUID id) {
        Item foundItem = itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        return ItemMapper.mapToResponse(foundItem);
    }


}
