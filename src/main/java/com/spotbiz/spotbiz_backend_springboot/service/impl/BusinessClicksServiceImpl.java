package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessClicksDto;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessClicks;
import com.spotbiz.spotbiz_backend_springboot.mapper.BusinessClicksMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessClicksRepo;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BusinessClicksServiceImpl implements BusinessClickService {

    @Autowired
    private BusinessClicksRepo businessClicksRepo;

    @Autowired
    private BusinessClicksMapper businessClicksMapper;

    @Override
    public BusinessClicksDto getBusinessClicks(Integer BusinessId) {

        try {
            Optional<BusinessClicks> businessClicks = businessClicksRepo.findByBusinessId(BusinessId);

            if (businessClicks.isPresent()) {
                return businessClicksMapper.toBusinessClicksDto(businessClicks.get());
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get clicks: " + e.getMessage());
        }
    }

    @Override
    public BusinessClicksDto handleBusinessClicks(BusinessClicksDto businessClicksDto) {
        BusinessClicks businessClicks1 = businessClicksMapper.toBusinessClicks(businessClicksDto);

        try {
            Optional<BusinessClicks> businessClicks = businessClicksRepo.findByBusinessId(businessClicksDto.getBusinessId());
            System.out.println(businessClicks.isPresent());
           if(businessClicks.isPresent()){
                businessClicks1.setClicks(businessClicks.get().getClicks() + 1);
                businessClicks1.setBusinessClickId(businessClicks.get().getBusinessClickId());
           }
            businessClicksRepo.save(businessClicks1);
            return businessClicksMapper.toBusinessClicksDto(businessClicks1);

        } catch (Exception e) {
            throw new RuntimeException("Failed to insert click: " + e.getMessage());
        }
    }
}
