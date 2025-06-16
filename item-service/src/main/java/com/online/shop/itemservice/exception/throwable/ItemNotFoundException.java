package com.online.shop.itemservice.exception.throwable;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException() {
        super("Item with given Id doesn't exist");
    }
}
