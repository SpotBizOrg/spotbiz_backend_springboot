package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.SubscribeDto;
import java.time.LocalDateTime;
import java.util.List;

public interface SubscribeService {
    SubscribeDto subscribe(SubscribeDto subscribeDto);
    int unsubscribe(int userId, int businessId);
    List<SubscribeDto> getSubscribedBusinesses(int userId);
    List<SubscribeDto> getSubscribers(int businessId);
    int getSubscriberCount(int businessId);
}
