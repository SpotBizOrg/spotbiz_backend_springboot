package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.CustomerChosenPicResponseDto;
import com.spotbiz.spotbiz_backend_springboot.dto.CustomerDto;
import com.spotbiz.spotbiz_backend_springboot.entity.ChosenPics;
import com.spotbiz.spotbiz_backend_springboot.entity.CustomerPics;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.repo.ChosenPicsRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.CustomerPicsRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import com.spotbiz.spotbiz_backend_springboot.service.CustomerChosenPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerChosenPicServiceImpl implements CustomerChosenPicService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CustomerPicsRepo customerPicsRepo;

    @Autowired
    private ChosenPicsRepo chosenPicsRepo;

    @Override
    public CustomerDto saveChosenPic(CustomerDto customerDto) {
        // Fetch the user
        Optional<User> optionalUser = userRepo.findById(customerDto.getUserId());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + customerDto.getUserId());
        }

        // Fetch the picture
        Optional<CustomerPics> optionalCustomerPics = customerPicsRepo.findById(customerDto.getPicId());
        if (optionalCustomerPics.isEmpty()) {
            throw new RuntimeException("Picture not found with ID: " + customerDto.getPicId());
        }

        User user = optionalUser.get();
        CustomerPics customerPics = optionalCustomerPics.get();

        // Check if a record exists in chosen_pics
        Optional<ChosenPics> optionalChosenPics = chosenPicsRepo.findChosenPicsByUser(user);

        ChosenPics chosenPics;
        if (optionalChosenPics.isEmpty()) {
            // Create a new record
            chosenPics = new ChosenPics(0, user, customerPics);
        } else {
            // Update existing record
            chosenPics = optionalChosenPics.get();
            chosenPics.setCustomerPics(customerPics);
        }

        // Save the chosenPics and return the result
        ChosenPics savedChosenPics = chosenPicsRepo.save(chosenPics);

        return new CustomerDto(savedChosenPics.getUser().getUserId(), savedChosenPics.getCustomerPics().getPicId());
    }


    @Override
    public CustomerChosenPicResponseDto getChosenPic(int customerId) {
        try {
            Optional<User> optionalUser = userRepo.findById(customerId);

            if (optionalUser.isEmpty()){
                throw new RuntimeException("User not found");
            }

            System.out.println("user found");

            User user = optionalUser.get();

            Optional<ChosenPics> optionalChosenPics = chosenPicsRepo.findChosenPicsByUser(user);

            if (optionalChosenPics.isEmpty()) {
                return null;
            }
            return new CustomerChosenPicResponseDto(user.getUserId(), optionalChosenPics.get().getCustomerPics().getPicId(), optionalChosenPics.get().getCustomerPics().getImageUrl());
        }catch (RuntimeException e) {
            throw new RuntimeException("Failed to get the customer pics");
        }

    }


}
