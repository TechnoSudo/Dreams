package com.example.demo.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dreams.backend.dto.item.ItemDto;

@Data
@AllArgsConstructor
public class CartItemDto {
    private ItemDto item;
    private int quantity;
}