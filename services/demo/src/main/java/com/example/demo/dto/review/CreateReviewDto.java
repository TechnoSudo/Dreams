package com.example.demo.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateReviewDto {
    @Min(1) @Max(5)
    private int rating;
    @Size(max = 500)
    private String comment;
}