package com.aurora.redisPoC.controller;

import com.aurora.redisPoC.model.ItemDto;
import com.aurora.redisPoC.repository.ItemDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("demo/")
public class ItemController {

    private final ItemDao itemDao;

    ThreadLocalRandom random = ThreadLocalRandom.current();

    public ItemController(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @GetMapping(value = "/items", produces = APPLICATION_JSON_VALUE)
    public List<ItemDto> items() throws InterruptedException {
        return itemDao.getItems();
    }

    @GetMapping(value = "/items/{itemId}", produces = APPLICATION_JSON_VALUE)
    public ItemDto items(@PathVariable String itemId) throws InterruptedException {
        return itemDao.getItem(itemId).get();
    }

    @PostMapping(value = "/items")
    public ResponseEntity<ItemDto> addItem(@RequestBody ItemDto itemDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemDao.addItem(itemDto));
    }

    @PutMapping(value = "/items/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable String itemId,
                                              @RequestBody ItemDto itemDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemDao.updateItem(itemId, itemDto));
    }
}
