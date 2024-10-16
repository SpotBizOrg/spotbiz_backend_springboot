package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.Notification;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface NotificationRepo extends JpaRepository<Notification, Integer> {
    @Query("SELECT n FROM Notification n WHERE n.user = :user AND n.business IS NULL AND n.type IN (com.spotbiz.spotbiz_backend_springboot.entity.NotificationType.COUPON, com.spotbiz.spotbiz_backend_springboot.entity.NotificationType.BROADCAST) ORDER BY n.dateTime DESC")
    List<Notification> findByUserAndType(@Param("user") User user);
}
