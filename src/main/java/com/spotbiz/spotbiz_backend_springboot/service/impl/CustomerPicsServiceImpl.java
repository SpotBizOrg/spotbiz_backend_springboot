package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.CustomerPicsDto;
import com.spotbiz.spotbiz_backend_springboot.entity.CustomerPics;
import com.spotbiz.spotbiz_backend_springboot.mapper.CustomerPicMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.CustomerPicsRepo;
import com.spotbiz.spotbiz_backend_springboot.service.CustomerPicsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerPicsServiceImpl implements CustomerPicsService {

    private final CustomerPicsRepo customerPicsRepo;
    private final CustomerPicMapper customerPicMapper;

    public CustomerPicsServiceImpl(CustomerPicsRepo customerPicsRepo, CustomerPicMapper customerPicMapper) {
        this.customerPicsRepo = customerPicsRepo;
        this.customerPicMapper = customerPicMapper;
    }

    @Override
    public CustomerPics savePics(CustomerPicsDto customerPicsDto) {
        try{
            CustomerPics customerPics = customerPicMapper.toCustomerPics(customerPicsDto);
            System.out.println(customerPics.getImageUrl());
            return customerPicsRepo.save(customerPics);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while saving the pic");
        }
    }

    @Override
    public List<CustomerPics> getAllCustomerPics() {
        try {
            return customerPicsRepo.findAll();
        }catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching the pics");
        }
    }

    @Override
    public CustomerPics updatePic(CustomerPicsDto customerPicsDto) {
        return null;
    }

    @Override
    public CustomerPics deletePic(int customerPicsId) {
        return null;
    }
}
