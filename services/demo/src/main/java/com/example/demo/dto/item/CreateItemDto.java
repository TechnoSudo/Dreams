package com.example.demo.dto.item;

import lombok.Data;
import org.dreams.backend.entity.ItemEntity;
import org.dreams.backend.enums.Currency;

import java.util.List;

@Data
public class CreateItemDto {
    private String name;
    private String description;
    private int price;
    private Currency currency;
    private int quantity;
    private List<String> tags;

    public ItemEntity toEntity() {
        return new ItemEntity(
                null,
                null,
                name,
                description,
                price,
                currency,
                quantity,
                0.0
        );
    };
}
