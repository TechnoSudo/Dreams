package org.dreams.backend.controller;

import lombok.AllArgsConstructor;
import org.dreams.backend.dto.item.CreateItemDto;
import org.dreams.backend.dto.item.ItemDto;
import org.dreams.backend.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/items")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{itemUuid}")
    public ResponseEntity<ItemDto> getItem(@PathVariable UUID itemUuid) {
        return ResponseEntity.ok(itemService.getItem(itemUuid));
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItems(
            @RequestParam(defaultValue = "10") int records,
            @RequestParam(required = false) List<String> tags) {
        return ResponseEntity.ok(itemService.getItems(records, tags != null ? tags : List.of()));
    }

    @PostMapping
    public ResponseEntity<ItemDto> addItem(@RequestBody CreateItemDto createItemDto) {
        return ResponseEntity.ok(itemService.addItem(createItemDto));
    }
}
