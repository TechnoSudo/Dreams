package org.dreams.backend.controller;

import lombok.AllArgsConstructor;
import org.dreams.backend.dto.cart.AddCartItemDto;
import org.dreams.backend.dto.cart.CartDto;
import org.dreams.backend.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/carts")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userUuid}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID userUuid) {
        return ResponseEntity.ok(cartService.getCart(userUuid));
    }

    @PostMapping("/{userUuid}/items")
    public ResponseEntity<CartDto> addItemToCart(
            @PathVariable UUID userUuid,
            @RequestBody AddCartItemDto addCartItemDto) {
        try {
            return ResponseEntity.ok(cartService.addItemToCart(addCartItemDto, userUuid));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
