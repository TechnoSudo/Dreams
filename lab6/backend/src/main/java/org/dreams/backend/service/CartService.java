package org.dreams.backend.service;

import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.dreams.backend.dto.cart.AddCartItemDto;
import org.dreams.backend.dto.cart.CartDto;
import org.dreams.backend.dto.cart.CartItemDto;
import org.dreams.backend.entity.CartItemEntity;
import org.dreams.backend.entity.ItemEntity;
import org.dreams.backend.repository.CartRepository;
import org.dreams.backend.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {

    private ItemRepository itemRepository;
    private CartRepository cartRepository;

    public CartDto getCart(UUID userUuid) {
        var cartItemEntities = cartRepository.findAllByUserUuid(userUuid);
        var items = itemRepository.findAllByUuidIsIn(cartItemEntities.stream()
                .map(CartItemEntity::itemUuid)
                .toList()).stream().map(ItemEntity::toDto).toList();
        var cartItems = cartItemEntities.stream().map(entity -> new CartItemDto(
                items.stream().filter(itemDto -> itemDto.getUuid().equals(entity.itemUuid())).findFirst().orElse(null),
                entity.quantity()
        )).toList();
        return new CartDto(0.0, cartItems);
    }

    public CartDto addItemToCart(AddCartItemDto addCartItemDto, UUID userUuid) throws BadRequestException {
        var item = itemRepository.findById(addCartItemDto.getItemUuid()).orElseThrow();
        if (item.quantity() < addCartItemDto.getQuantity())
            throw new BadRequestException();
        cartRepository.save(new CartItemEntity(null, addCartItemDto.getItemUuid(), userUuid, addCartItemDto.getQuantity()));
        return getCart(userUuid);
    }

    public CartDto updateItemCard(AddCartItemDto addCartItemDto, UUID userUuid) throws BadRequestException {
        var item = itemRepository.findById(addCartItemDto.getItemUuid()).orElseThrow();
        if (item.quantity() < addCartItemDto.getQuantity())
            throw new BadRequestException();
        if (addCartItemDto.getQuantity() == 0)
            cartRepository.removeCartItemEntitiesByItemUuid(addCartItemDto.getItemUuid());
        else {
            var cartItem = cartRepository.findFirstByItemUuid(addCartItemDto.getItemUuid());
            var toUpdate = new CartItemEntity(cartItem.uuid(), cartItem.itemUuid(), null, addCartItemDto.getQuantity());
            cartRepository.save(cartItem);
        }
        return getCart(userUuid);
    }
}
