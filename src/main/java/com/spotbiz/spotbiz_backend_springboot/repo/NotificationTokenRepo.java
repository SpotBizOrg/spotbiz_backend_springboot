package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.NotificationToken;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface NotificationTokenRepo extends JpaRepository<NotificationToken, Integer> {
    NotificationToken findByUser(User user);
    NotificationToken findTokenByUser(User user);
}
