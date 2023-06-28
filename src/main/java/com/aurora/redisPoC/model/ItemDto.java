package com.aurora.redisPoC.model;


public record ItemDto(String itemId, String name, double price) {

    public ItemDto(String name, double price) {
        this(null, name, price);
    }
}