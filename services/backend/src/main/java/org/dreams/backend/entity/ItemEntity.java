package org.dreams.backend.entity;

import jakarta.persistence.*;
import org.dreams.backend.dto.item.ItemDto;
import org.dreams.backend.enums.Currency;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Entity
@Table(name = "item")
public record ItemEntity(

        @Id @jakarta.persistence.Id
        UUID uuid,

        UUID vendorUuid,

        String name,
        String description,
        int price,
        Currency currency,
        int quantity,
        double rating
) {

        public ItemDto toDto() {
                return new ItemDto(
                        uuid,
                        vendorUuid,
                        name,
                        description,
                        price,
                        currency,
                        quantity,
                        rating
                );
        }
}
