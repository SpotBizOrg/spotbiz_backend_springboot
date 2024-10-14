package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.NotificationTokenDto;

public interface NotificationTokenService {
    NotificationTokenDto insertNotificationToken(NotificationTokenDto notificationTokenDto);
    String getTokenByUserId(int userId);
}
