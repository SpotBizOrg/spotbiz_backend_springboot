package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementDto;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDto;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessOwnerDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Advertisement;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import java.util.List;
import com.spotbiz.spotbiz_backend_springboot.mapper.AdvertisementMapper;
import com.spotbiz.spotbiz_backend_springboot.mapper.BusinessMapper;
import com.spotbiz.spotbiz_backend_springboot.mapper.UserMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.AdvertisementRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessOwnerService {

    @Autowired
    private BusinessRepo businessRepo;
    @Autowired
    private AdvertisementRepo advertisementRepo;
    @Autowired
    private AdvertisementMapper advertisementMapper;
    @Autowired
    private BusinessMapper businessMapper;
    @Autowired
    private UserMapper userMapper;

    public BusinessOwnerDto getDetails(User user) {
        return userMapper.toBusinessOwnerDto(user);
    }

    public BusinessDto getBusinessDetails(User user) {
        BusinessDto dto = new BusinessDto();
        Integer userId = user.getUserId();

        Business business = businessRepo.findByUserUserId(userId);

        if (business != null) {
            return businessMapper.toBusinessDto(business);
        }
        return dto;
    }

    public List<AdvertisementDto> getAdvertisements(User user){

        Integer userId = user.getUserId();
        Business business = businessRepo.findByUserUserId(userId);
        if (business != null) {
            List<Advertisement> ads = advertisementRepo.findByBusinessBusinessId(business.getBusinessId());
            return advertisementMapper.mapToAdvertisementDtos(ads);
        }

        return null;
    }


}
