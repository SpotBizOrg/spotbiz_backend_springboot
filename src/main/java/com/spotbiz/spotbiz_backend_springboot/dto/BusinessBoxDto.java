package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessBoxDto {

    private Integer businessId;
    private String name;
    private String logo;
    private String description;
    private String address;
    private Double avgRating;
    private String status;
    private List<String> tags;
    private WeeklyScheduleDto weeklySchedule;
}
