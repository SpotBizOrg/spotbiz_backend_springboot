package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.Data;

@Data
public class RatingStatsDto {
    private double averageRating;
    private long numberOfRatings;
}
