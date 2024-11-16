package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface PackageRepo extends JpaRepository<Package, Long> {

    Package findByPackageId(int packageId);

    boolean existsByPackageId(int packageId);

    void deleteByPackageId(int packageId);

    @Query(value = "SELECT * FROM packages ORDER BY id ASC", nativeQuery = true)
    List<Package> findAllOrderedByPackageIdAsc();
}
