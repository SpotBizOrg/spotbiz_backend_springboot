package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.BusinessAppeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessAppealRepo extends JpaRepository<BusinessAppeal, Integer> {
    List<BusinessAppeal> findBusinessAppealsByStatus(String status);
}
