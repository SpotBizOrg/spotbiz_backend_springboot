package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Integer reviewId;
    private String title;
    private String description;
    private LocalDateTime date;
    private String username;
    private Integer businessId;
    private Integer rating;
    private String status;



}
