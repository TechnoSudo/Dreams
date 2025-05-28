package com.example.demo.entity;

import jakarta.persistence.Entity;
import org.dreams.backend.dto.review.ReviewDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Entity
@Table(name = "review")
public record ReviewEntity(

       @Id @jakarta.persistence.Id
       UUID uuid,

       UUID itemUuid,
       UUID userUuid,
       int rating,
       String comment
) {
       public ReviewDto toDto() {
              return new ReviewDto(uuid, itemUuid, userUuid, rating, comment);
       }
}
