package com.spotbiz.spotbiz_backend_springboot.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpeningHoursDto {
    @JsonProperty("isOpen")
    private boolean isOpen;
    private String startTime;
    private String endTime;
    private String specialNote;

}

