package org.dreams.backend.controller;

import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.dreams.backend.dto.cart.AddCartItemDto;
import org.dreams.backend.dto.cart.CartDto;
import org.dreams.backend.dto.item.CreateItemDto;
import org.dreams.backend.dto.item.ItemDto;
import org.dreams.backend.dto.review.CreateReviewDto;
import org.dreams.backend.dto.review.ReviewDto;
import org.dreams.backend.service.CartService;
import org.dreams.backend.service.ItemService;
import org.dreams.backend.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/store")
@AllArgsConstructor
public class StoreController {

    private final ItemService itemService;
    private final CartService cartService;
    private final ReviewService reviewService;

    @GetMapping
    public String storeFront(@RequestParam(defaultValue = "10") int records,
                             @RequestParam(required = false) List<String> tags,
                             Model model) {
        List<ItemDto> items = itemService.getItems(records, tags != null ? tags : List.of());

        Map<String, Object> jsonLd = new LinkedHashMap<>();
        jsonLd.put("@context", "https://schema.org");
        jsonLd.put("@type", "ItemList");

        List<Map<String, Object>> listItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            ItemDto item = items.get(i);
            Map<String, Object> listItem = new LinkedHashMap<>();
            listItem.put("@type", "ListItem");
            listItem.put("position", i + 1);
            listItem.put("item", Map.of(
                    "@type", "Product",
                    "name", item.getName(),
                    "description", item.getDescription(),
                    "sku", item.getUuid().toString(),
                    "image", item.getImageUrl(),
                    "offers", Map.of(
                            "@type", "Offer",
                            "priceCurrency", item.getCurrency(),
                            "price", item.getPrice(),
                            "availability", item.getQuantity() > 0 ? "https://schema.org/InStock" : "https://schema.org/OutOfStock"
                    )
            ));
            listItems.add(listItem);
        }

        jsonLd.put("itemListElement", listItems);

        model.addAttribute("items", items);
        model.addAttribute("jsonLd", jsonLd);
        return "store/index";
    }

    @GetMapping("/item/{itemUuid}")
    public String itemDetails(@PathVariable UUID itemUuid, Model model) {
        ItemDto item = itemService.getItem(itemUuid);
        item.setRating(3.0);
        List<ReviewDto> reviews = reviewService.getReviewsForItem(itemUuid);

        Map<String, Object> jsonLd = new LinkedHashMap<>();
        jsonLd.put("@context", "https://schema.org");
        jsonLd.put("@type", "Product");
        jsonLd.put("name", item.getName());
        jsonLd.put("description", item.getDescription());
        jsonLd.put("image", item.getImageUrl());
        jsonLd.put("sku", item.getUuid().toString());

        Map<String, Object> offer = new LinkedHashMap<>();
        offer.put("@type", "Offer");
        offer.put("price", item.getPrice());
        offer.put("priceCurrency", item.getCurrency());
        offer.put("availability", item.getQuantity() > 0 ? "https://schema.org/InStock" : "https://schema.org/OutOfStock");
        jsonLd.put("offers", offer);


        Map<String, Object> aggregateRating = new LinkedHashMap<>();
        aggregateRating.put("@type", "AggregateRating");
        aggregateRating.put("ratingValue", 3);
        aggregateRating.put("ratingCount", 12);
        jsonLd.put("aggregateRating", aggregateRating);

        if (!reviews.isEmpty()) {
            List<Map<String, Object>> jsonReviews = new ArrayList<>();
            for (ReviewDto review : reviews) {
                Map<String, Object> jsonReview = new LinkedHashMap<>();
                jsonReview.put("@type", "Review");
                jsonReview.put("reviewBody", review.getComment());
                jsonReview.put("author", Map.of(
                        "@type", "Person",
                        "name", "User" // You can use review.getUserName() if available
                ));
                jsonReview.put("reviewRating", Map.of(
                        "@type", "Rating",
                        "itemReviewed", item.getUuid(),
                        "ratingValue", review.getRating(),
                        "bestRating", 5
                ));
                jsonReviews.add(jsonReview);
            }
            jsonLd.put("review", jsonReviews);
        }

        model.addAttribute("jsonLd", jsonLd);

        model.addAttribute("item", item);
        model.addAttribute("reviews", reviews);
        model.addAttribute("addToCartDto", new AddCartItemDto());
        model.addAttribute("createReviewDto", new CreateReviewDto());

        return "store/item-details";
    }

    @PostMapping("/item/{itemUuid}/add-to-cart")
    public String addToCart(@PathVariable UUID itemUuid,
                            @ModelAttribute AddCartItemDto addCartItemDto) throws BadRequestException {
        // In a real app, you'd get the userUuid from the session/authentication
        addCartItemDto.setItemUuid(itemUuid);
        cartService.addItemToCart(addCartItemDto, null);
        return "redirect:/store/item/" + itemUuid + "?addedToCart=true";
    }

    @GetMapping("/add-item")
    public String showAddItemForm(Model model) {
        model.addAttribute("createItemDto", new CreateItemDto());
        return "store/add-item";
    }

    @PostMapping("/add-item")
    public String addItem(@ModelAttribute CreateItemDto createItemDto,
                          RedirectAttributes redirectAttributes) {
        try {
            ItemDto newItem = itemService.addItem(createItemDto);
            redirectAttributes.addFlashAttribute("successMessage", "Item added successfully!");
            return "redirect:/store/item/" + newItem.getUuid();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding item: " + e.getMessage());
            return "redirect:/store/add-item";
        }
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        CartDto cart = cartService.getCart(null);
        model.addAttribute("cart", cart);
        return "store/cart";
    }

    @PostMapping("/cart/remove-item")
    public String removeFromCart(@RequestParam UUID itemUuid) throws BadRequestException {
        cartService.updateItemCard(new AddCartItemDto(itemUuid, 0), null);
        return "redirect:/store/cart";
    }

    @PostMapping("/cart/update-quantity")
    public String updateQuantity(@RequestParam UUID itemUuid, @RequestParam int quantity) throws BadRequestException {
        cartService.updateItemCard(new AddCartItemDto(itemUuid, quantity), null);
        return "redirect:/store/cart";
    }

    @PostMapping("/item/{itemUuid}/add-review")
    public String addReview(@PathVariable UUID itemUuid,
                            @ModelAttribute CreateReviewDto createReviewDto,
                            RedirectAttributes redirectAttributes) {
        try {
            // In a real app, you'd get the userUuid from the session/authentication
            ReviewDto reviewDto = new ReviewDto(
                    null, // review UUID will be generated
                    itemUuid,
                    null, // Temporary - replace with authenticated user
                    createReviewDto.getRating(),
                    createReviewDto.getComment()
            );

            reviewService.createReview(reviewDto);
            redirectAttributes.addFlashAttribute("successMessage", "Review added successfully!");
            return "redirect:/store/item/" + itemUuid;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding review: " + e.getMessage());
            return "redirect:/store/item/" + itemUuid;
        }
    }
}