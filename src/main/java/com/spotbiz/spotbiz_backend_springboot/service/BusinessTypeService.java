package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.entity.BusinessType;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessTypeService {
    @Autowired
    private BusinessTypeRepository businessTypeRepository;

    public List<BusinessType> getAllBusinessTypes() {
        return businessTypeRepository.findAll();
    }
}
