package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.OpeningHoursDto;
import com.spotbiz.spotbiz_backend_springboot.dto.WeeklyScheduleDto;

public interface OpeningHoursService {


    WeeklyScheduleDto getOpeningHours(String businessEmail);
    WeeklyScheduleDto updateOpeningHours(String businessEmail, WeeklyScheduleDto openingHoursDTO);
}
