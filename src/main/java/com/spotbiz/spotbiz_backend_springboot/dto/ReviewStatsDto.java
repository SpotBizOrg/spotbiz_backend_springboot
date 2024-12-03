package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewStatsDto {
    private double averageRating;
    private double oneStars;
    private double twoStars;
    private double threeStars;
    private double fourStars;
    private double fiveStars;
    private long numberOfRatings;
}
