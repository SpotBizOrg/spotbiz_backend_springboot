package com.spotbiz.spotbiz_backend_springboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeeklyScheduleDto {
    @JsonProperty("Monday")
    private OpeningHoursDto monday;
    @JsonProperty("Tuesday")
    private OpeningHoursDto tuesday;
    @JsonProperty("Wednesday")
    private OpeningHoursDto wednesday;
    @JsonProperty("Thursday")
    private OpeningHoursDto thursday;
    @JsonProperty("Friday")
    private OpeningHoursDto friday;
    @JsonProperty("Saturday")
    private OpeningHoursDto saturday;
    @JsonProperty("Sunday")
    private OpeningHoursDto sunday;
}
