package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDataDto {

    private Integer businessId;
    private String name;
    private String logo;
    private String description;
    private String address;
    private Double avgRating;
    private int reviewCount;
    private String email;
    private String phone;
    private List<String> tags;
    private WeeklyScheduleDto weeklySchedule;
}
