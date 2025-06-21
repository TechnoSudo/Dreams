package org.dreams.backend.entity;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Entity
@Table(name = "cart_item")
public record CartItemEntity(

        @Id @jakarta.persistence.Id
        UUID uuid,

        UUID itemUuid,
        UUID userUuid,
        int quantity
) {
}
