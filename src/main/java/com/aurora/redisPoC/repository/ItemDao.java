package com.aurora.redisPoC.repository;

import com.aurora.redisPoC.model.ItemDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.*;

import static java.util.concurrent.TimeUnit.SECONDS;

@Repository
@Slf4j
public class ItemDao {
    static int id = 100;
    private final Map<String, ItemDto> itemList = new HashMap<>();

    public ItemDao() {
        addItems();
    }

    public void addItems() {
        String id = createAndGetId();
        itemList.put(
                id,
                new ItemDto(id, "tshirt", 59.99));
        id = createAndGetId();
        itemList.put(
                id,
                new ItemDto(id, "trousers", 79.99));
    }

    private String createAndGetId() {
        return "I" + id++;
    }

    @Cacheable("items")
    public List<ItemDto> getItems() throws InterruptedException {
        log.info("Calling service to get Items data...");
        SECONDS.sleep(5);
        log.debug("This is debug Calling service to get Items data...");
        return new ArrayList<>(itemList.values());
    }

    @CacheEvict(value = "items", allEntries = true) //doing allEntries for demonstration purposes. We could pick specific keys to evict from our cache based on certain conditions
    public ItemDto addItem(ItemDto item) {
        String id = createAndGetId();
        ItemDto itemDto =
                new ItemDto(id, item.name(), item.price());
        itemList.put(id, itemDto);
        return itemDto;
    }

    @CachePut(value = "items", key = "#itemId")
    public ItemDto updateItem(String itemId, ItemDto item) {
        ItemDto itemDto =
                new ItemDto(itemId, item.name(), item.price());
        itemList.put(itemId, itemDto);
        return itemDto;
    }

    @Cacheable(value = "items", key = "#itemId")
    public Optional<ItemDto> getItem(String itemId) {
        log.info("Finding item: " + itemId);
        sleep(2);
        return Optional.ofNullable(itemList.get(itemId));
    }

    private void sleep(int seconds) {
        try {
            SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

