package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.SubscribeDto;
import com.spotbiz.spotbiz_backend_springboot.entity.*;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.NotificationRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import com.spotbiz.spotbiz_backend_springboot.service.NotificationService;
import com.spotbiz.spotbiz_backend_springboot.service.NotificationTokenService;
import com.spotbiz.spotbiz_backend_springboot.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepo notificationRepo;
    private final UserRepo userRepo;
    private final BusinessRepo businessRepo;
    private final NotificationTokenService notificationTokenService;
    private final SubscribeService subscribeService;

    @Autowired
    public NotificationServiceImpl(NotificationRepo notificationRepo, UserRepo userRepo, BusinessRepo businessRepo, NotificationTokenService notificationTokenService, SubscribeService subscribeService) {
        this.notificationRepo = notificationRepo;
        this.userRepo = userRepo;
        this.businessRepo = businessRepo;
        this.notificationTokenService = notificationTokenService;
        this.subscribeService = subscribeService;
    }

    @Override
    public Notification insertNotification(Notification notification){
        if(notification.getType() != NotificationType.BROADCAST && notification.getUser() == null && notification.getBusiness() == null){
            throw new RuntimeException("Invalid data provided!");
        }
        return notificationRepo.save(notification);
    }

    @Override
    public List<Notification> getAllNotification(int userId) {
        return notificationRepo.findByUserAndType(userRepo.findByUserId(userId));
    }

    @Override
    public void sendNotification(String title, String body, NotificationType type, int id){
        try{
            Notification notificationObject = new Notification();
            notificationObject.setType(type);
            notificationObject.setTitle(title);
            notificationObject.setDescription(body);
            notificationObject.setDateTime(LocalDateTime.now());

            if(type == NotificationType.COUPON){
                User user = userRepo.findByUserId(id);
                if (user == null) {
                    System.out.println("User not found for ID: " + id);
                    throw new RuntimeException("User not found!");
                }
                notificationObject.setUser(user);
            }
            else if(type == NotificationType.PROMOTION){
                Business business = businessRepo.findByBusinessId(id);
                if (business == null) {
                    System.out.println("Business not found for ID: " + id);
                    throw new RuntimeException("Business not found!");
                }
                notificationObject.setBusiness(business);
            }
            insertNotification(notificationObject);

            FcmService fcmService = new FcmService();

            if(type == NotificationType.COUPON){
                String token = notificationTokenService.getTokenByUserId(id);
                fcmService.sendNotificationToDevice(token, title, body);
            }
            else if(type == NotificationType.PROMOTION){
                List<SubscribeDto> subscribedList = subscribeService.getSubscribers(id);
                for (SubscribeDto subscribe : subscribedList) {
                    String token = notificationTokenService.getTokenByUserId(subscribe.getUserId());
                    fcmService.sendNotificationToDevice(token, title, body);
                }
            }
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
