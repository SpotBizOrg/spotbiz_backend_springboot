package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.Subscribe;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface SubscribeRepo extends JpaRepository<Subscribe, Integer> {
    Subscribe findOneByUserAndBusiness(User user, Business business);
    List<Subscribe> findByUser(User user);
    List<Subscribe> findByBusiness(Business business);
    int countByBusiness(Business business);
}
