package org.dreams.backend.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dreams.backend.enums.Currency;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private UUID uuid;
    private UUID vendorUuid;
    private String name;
    private String description;
    private int price;
    private Currency currency;
    private int quantity;
    private double rating;
}
