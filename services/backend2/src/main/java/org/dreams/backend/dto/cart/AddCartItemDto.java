package org.dreams.backend.dto.cart;

import lombok.Data;
import org.dreams.backend.entity.CartItemEntity;

import java.util.UUID;

@Data
public class AddCartItemDto {
    private UUID itemUuid;
    private int quantity;

    public CartItemEntity toEntity(UUID userUuid) {
        return new CartItemEntity(itemUuid, userUuid, quantity);
    }
}
