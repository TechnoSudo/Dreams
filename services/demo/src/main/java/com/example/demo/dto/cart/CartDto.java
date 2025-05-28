package com.example.demo.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartDto {
    private double totalPrice;
    List<CartItemDto> items;
}
