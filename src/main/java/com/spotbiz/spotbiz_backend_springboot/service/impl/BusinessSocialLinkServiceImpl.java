package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessSocialLinkDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessSocialLink;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessSocialLinkRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessSocialLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessSocialLinkServiceImpl implements BusinessSocialLinkService {
    @Autowired
    private BusinessSocialLinkRepo repository;


    @Autowired
    private BusinessRepo businessRepository;


    @Autowired
    private UserRepo userRepository;


    @Override
    public List<BusinessSocialLink> UpdateSocialLinks(String email, List<BusinessSocialLinkDto> updatedLinks) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found for email: " + email);
        }

        Integer userId = userOptional.get().getUserId();
        Optional<Business> businessOptional = Optional.ofNullable(businessRepository.findByUserUserId(userId));
        if (businessOptional.isEmpty()) {
            throw new RuntimeException("Business not found for user ID: " + userId);
        }

        Integer businessId = businessOptional.get().getBusinessId();

        List<BusinessSocialLink> updatedSocialLinks = new ArrayList<>();

        for (BusinessSocialLinkDto updatedLink : updatedLinks) {
            Optional<BusinessSocialLink> optionalLink = repository.findByBusinessIdAndType(businessId, updatedLink.getType());

            if (optionalLink.isPresent()) {
                BusinessSocialLink existingLink = optionalLink.get();
                existingLink.setLink(updatedLink.getLink());
                updatedSocialLinks.add(repository.save(existingLink));
            } else {
                BusinessSocialLink newLink = new BusinessSocialLink();
                newLink.setBusinessId(businessId);
                newLink.setType(updatedLink.getType());
                newLink.setLink(updatedLink.getLink());
                updatedSocialLinks.add(repository.save(newLink));
            }
        }

        return updatedSocialLinks;
    }



    @Override
    public List<BusinessSocialLink> getSocialLinks(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found for email: " + email);
        }
        Integer userId = user.get().getUserId();
        Optional<Business> businessOptional = Optional.ofNullable(businessRepository.findByUserUserId(userId));
        if (businessOptional.isEmpty()) {
            throw new RuntimeException("Business not found for user ID: " + userId);
        }
        Integer businessId = businessOptional.get().getBusinessId();
        return repository.findByBusinessId(businessId);

    }


}
