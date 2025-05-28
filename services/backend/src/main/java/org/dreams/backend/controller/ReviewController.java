package org.dreams.backend.controller;

import lombok.AllArgsConstructor;
import org.dreams.backend.dto.review.ReviewDto;
import org.dreams.backend.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/items/{itemUuid}/reviews")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(
            @PathVariable UUID itemUuid,
            @RequestBody ReviewDto reviewDto) {
        reviewDto.setItemUuid(itemUuid);
        return ResponseEntity.ok(reviewService.createReview(reviewDto));
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getReviewsForItem(@PathVariable UUID itemUuid) {
        return ResponseEntity.ok(reviewService.getReviewsForItem(itemUuid));
    }
}
