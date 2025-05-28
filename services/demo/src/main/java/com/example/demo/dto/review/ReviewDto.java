package com.example.demo.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dreams.backend.entity.ReviewEntity;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ReviewDto {
    private UUID uuid;
    private UUID itemUuid;
    private UUID userUuid;
    private int rating;
    private String comment;

    public ReviewEntity toEntity() {
        return new ReviewEntity(this.uuid, this.itemUuid, this.userUuid, this.rating, this.comment);
    }
}
