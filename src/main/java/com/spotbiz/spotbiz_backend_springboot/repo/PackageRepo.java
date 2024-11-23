package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface PackageRepo extends JpaRepository<Package, Long> {

    Package findByPackageId(int packageId);

    boolean existsByPackageId(int packageId);

    void deleteByPackageId(int packageId);

    @Query(value = "SELECT * FROM packages ORDER BY id ASC", nativeQuery = true)
    List<Package> findAllOrderedByPackageIdAsc();


    @Query(value = "SELECT * FROM packages p\n" +
            "WHERE id = (\n" +
            "  SELECT sb.id FROM subscription_billing sb\n" +
            "  WHERE sb.billing_status = 'PAID' AND sb.is_active = true AND sb.business_id=:businessId\n" +
            ");\n", nativeQuery = true)
    Package findPackageByBusiness(@Param("businessId") Integer businessId);
}
