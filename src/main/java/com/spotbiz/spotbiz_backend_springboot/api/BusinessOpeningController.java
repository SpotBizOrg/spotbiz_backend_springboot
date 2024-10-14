package com.spotbiz.spotbiz_backend_springboot.api;


import com.spotbiz.spotbiz_backend_springboot.dto.OpeningHoursDto;
import com.spotbiz.spotbiz_backend_springboot.dto.WeeklyScheduleDto;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessService;
import com.spotbiz.spotbiz_backend_springboot.service.OpeningHoursService;
import com.spotbiz.spotbiz_backend_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/businessOpening")
public class BusinessOpeningController {

    @Autowired
    BusinessService businessService;

    @Autowired
    UserService userService;

    @Autowired
    OpeningHoursService openingHoursService;



    @GetMapping("/{email}")
    public ResponseEntity<WeeklyScheduleDto> getOpeningHours(@PathVariable String email) {
        WeeklyScheduleDto openingHours = openingHoursService.getOpeningHours(email);
        return ResponseEntity.ok(openingHours);
    }

    @PostMapping("/{email}")
    public ResponseEntity<WeeklyScheduleDto> updateOpeningHours(@PathVariable String email,
                                                                @RequestBody WeeklyScheduleDto openingHoursDTO) {

        WeeklyScheduleDto updatedOpeningHours = openingHoursService.updateOpeningHours(email, openingHoursDTO);
        return ResponseEntity.ok(updatedOpeningHours);
    }


}
