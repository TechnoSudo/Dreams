package org.dreams.backend.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dreams.backend.entity.CartItemEntity;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCartItemDto {
    private UUID itemUuid;
    private int quantity;

    public CartItemEntity toEntity(UUID userUuid) {
        return new CartItemEntity(null, itemUuid, userUuid, quantity);
    }
}
