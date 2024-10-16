package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.ReportedBusiness;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportedBusinessRepo extends JpaRepository<ReportedBusiness, Integer> {
}
