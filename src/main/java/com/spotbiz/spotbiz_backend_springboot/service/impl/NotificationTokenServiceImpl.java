package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.NotificationTokenDto;
import com.spotbiz.spotbiz_backend_springboot.entity.NotificationToken;
import com.spotbiz.spotbiz_backend_springboot.mapper.NotificationTokenMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.NotificationTokenRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import com.spotbiz.spotbiz_backend_springboot.service.NotificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationTokenServiceImpl implements NotificationTokenService {

    private final NotificationTokenRepo notificationTokenRepo;
    private final NotificationTokenMapper notificationTokenMapper;
    private final UserRepo userRepo;

    @Autowired
    public NotificationTokenServiceImpl(NotificationTokenRepo notificationTokenRepo, NotificationTokenMapper notificationTokenMapper, UserRepo userRepo) {

        this.notificationTokenRepo = notificationTokenRepo;
        this.notificationTokenMapper = notificationTokenMapper;
        this.userRepo = userRepo;
    }

    @Override
    public NotificationTokenDto insertNotificationToken(NotificationTokenDto notificationTokenDto) {
        NotificationToken notificationToken = notificationTokenMapper.toCoupon(notificationTokenDto);
        NotificationToken token = notificationTokenRepo.findByUser(notificationToken.getUser());
        if (token == null) {
            token = notificationTokenRepo.save(notificationToken);
            System.out.println(token.getNotificationTokenId());
            return notificationTokenMapper.toCouponDto(token);
        }
        else{
            token.setToken(notificationToken.getToken());
            token = notificationTokenRepo.save(token);
            System.out.println(token.getNotificationTokenId());
            return notificationTokenMapper.toCouponDto(token);
        }
    }

    @Override
    public String getTokenByUserId(int userId){
        return notificationTokenRepo.findTokenByUser(userRepo.findByUserId(userId)).getToken();
    }
}
