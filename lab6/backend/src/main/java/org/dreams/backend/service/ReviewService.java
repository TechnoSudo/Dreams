package org.dreams.backend.service;

import lombok.AllArgsConstructor;
import org.dreams.backend.dto.review.ReviewDto;
import org.dreams.backend.entity.ReviewEntity;
import org.dreams.backend.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewService {

    private ReviewRepository reviewRepository;

    public ReviewDto createReview(ReviewDto reviewDto) {
        var reviewEntity = reviewDto.toEntity();
        return reviewRepository.save(reviewEntity).toDto();
    }

    public List<ReviewDto> getReviewsForItem(UUID itemUuid) {
        return reviewRepository.findAllByItemUuid(itemUuid).stream()
                .map(ReviewEntity::toDto)
                .toList();
    }
}
