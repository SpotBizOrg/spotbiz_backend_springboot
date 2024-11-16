package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.entity.Notification;
import com.spotbiz.spotbiz_backend_springboot.entity.NotificationType;

import java.util.List;

public interface NotificationService {
    List<Notification> getAllNotification(int userId);
    Notification insertNotification(Notification notification);
    void sendNotification(String title, String body, NotificationType type, int id);
}
