package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotbiz.spotbiz_backend_springboot.dto.OpeningHoursDto;
import com.spotbiz.spotbiz_backend_springboot.dto.WeeklyScheduleDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessOpeningHours;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessOpeningHoursRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import com.spotbiz.spotbiz_backend_springboot.service.OpeningHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OpeningHoursServiceImpl implements OpeningHoursService {

    @Autowired
    BusinessRepo businessRepo;

    @Autowired
    BusinessOpeningHoursRepo businessOpeningHoursRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ObjectMapper objectMapper;



    @Override
    public WeeklyScheduleDto getOpeningHours(String businessEmail) {
        User user = userRepo.findByEmail(businessEmail)
                .orElseThrow(() -> new RuntimeException("Business not found"));
        Business business = businessRepo.findByUserUserId(user.getUserId());

        Optional<BusinessOpeningHours> openingHoursOpt = businessOpeningHoursRepo.findByBusinessId(business.getBusinessId());
        if (openingHoursOpt.isPresent()) {
            BusinessOpeningHours openingHoursEntity = openingHoursOpt.get();
            try {
                return objectMapper.readValue(openingHoursEntity.getOpeningHours(), WeeklyScheduleDto.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error processing JSON", e);
            }
        } else {
            return null;
        }
    }

    @Override
    public WeeklyScheduleDto updateOpeningHours(String businessEmail, WeeklyScheduleDto openingHoursDTO) {
        User user = userRepo.findByEmail(businessEmail)
                .orElseThrow(() -> new RuntimeException("Business not found"));
        Business business = businessRepo.findByUserUserId(user.getUserId());

        Optional<BusinessOpeningHours> openingHoursOpt = businessOpeningHoursRepo.findByBusinessId(business.getBusinessId());
        BusinessOpeningHours openingHoursEntity;

        if (openingHoursOpt.isPresent()) {
            openingHoursEntity = openingHoursOpt.get();
        } else {
            openingHoursEntity = new BusinessOpeningHours();
            openingHoursEntity.setBusiness(business);
        }

        try {
            String openingHoursJson = objectMapper.writeValueAsString(openingHoursDTO);
            openingHoursEntity.setOpeningHours(openingHoursJson);
            businessOpeningHoursRepo.save(openingHoursEntity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting opening hours to JSON", e);
        }

        return openingHoursDTO;
    }


}
