package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.SubscribeDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.Subscribe;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.mapper.SubscribeMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.SubscribeRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import com.spotbiz.spotbiz_backend_springboot.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubscribeServiceImpl implements SubscribeService {

    private final SubscribeRepo subscribeRepo;
    private final SubscribeMapper subscribeMapper;
    private final UserRepo userRepo;
    private final BusinessRepo businessRepo;

    @Autowired
    public SubscribeServiceImpl(SubscribeRepo subscribeRepo, SubscribeMapper subscribeMapper, UserRepo userRepo, BusinessRepo businessRepo) {
        this.subscribeRepo = subscribeRepo;
        this.subscribeMapper = subscribeMapper;
        this.userRepo = userRepo;
        this.businessRepo = businessRepo;
    }

    @Override
    public SubscribeDto subscribe(SubscribeDto subscribeDto) {
        try{
            Subscribe subscribe = subscribeMapper.toSubscribe(subscribeDto);
            return subscribeMapper.toSubscribeDto(subscribeRepo.save(subscribe));
        } catch (Exception e){
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }

    @Override
    public int unsubscribe(int userId, int businessId) {
        try{
            User user = userRepo.findById(userId).orElse(null);
            Business business = businessRepo.findByBusinessId(businessId);

            if(user != null && business != null) {
                Subscribe subscribe = subscribeRepo.findOneByUserAndBusiness(user, business);
                if(subscribe != null) {
                    subscribeRepo.delete(subscribe);
                    subscribe = subscribeRepo.findOneByUserAndBusiness(user, business);
                    if(subscribe == null) {
                        return 1;
                    }
                    throw new UsernameNotFoundException("Unsubscribe unsuccessful");
                }
                throw new UsernameNotFoundException("Subscribe not found");
            }
            throw new UsernameNotFoundException("User or Business not found");

        } catch (Exception e){
            throw new UsernameNotFoundException("Something went wrong");
        }
    }

    @Override
    public List<SubscribeDto> getSubscribedBusinesses(int userId) {
        try{
            User user = userRepo.findById(userId).orElse(null);
            if(user != null) {
                List<Subscribe> subscribedList = subscribeRepo.findByUser(user);
                if(subscribedList != null) {
                    List<SubscribeDto> subscribeDtos = new ArrayList<>();
                    for (Subscribe subscribe : subscribedList) {
                        SubscribeDto subscribeDto = subscribeMapper.toSubscribeDto(subscribe);
                        subscribeDtos.add(subscribeDto);
                    }
                    return subscribeDtos;
                }
                throw new UsernameNotFoundException("Subscribe list is empty");
            }
            throw new UsernameNotFoundException("User not found");

        } catch (Exception e){
            throw new UsernameNotFoundException("Something went wrong");
        }
    }

    @Override
    public List<SubscribeDto> getSubscribers(int businessId) {
        try{
            Business business = businessRepo.findByBusinessId(businessId);
            if(business != null) {
                List<Subscribe> subscribedList = subscribeRepo.findByBusiness(business);
                if(subscribedList != null) {
                    List<SubscribeDto> subscribeDtos = new ArrayList<>();
                    for (Subscribe subscribe : subscribedList) {
                        SubscribeDto subscribeDto = subscribeMapper.toSubscribeDto(subscribe);
                        subscribeDtos.add(subscribeDto);
                    }
                    return subscribeDtos;
                }
                throw new UsernameNotFoundException("Subscriber list is empty");
            }
            throw new UsernameNotFoundException("Business not found");

        } catch (Exception e){
            throw new UsernameNotFoundException("Something went wrong");
        }
    }

    @Override
    public int getSubscriberCount(int businessId) {
        try{
            Business business = businessRepo.findByBusinessId(businessId);
            if(business != null) {
                int subscribedCount = subscribeRepo.countByBusiness(business);
                if(subscribedCount >= 0) {
                    return subscribedCount;
                }
                throw new UsernameNotFoundException("Subscriber list is empty");
            }
            throw new UsernameNotFoundException("Business not found");
        } catch (Exception e){
            throw new UsernameNotFoundException("Something went wrong");
        }
    }
}
