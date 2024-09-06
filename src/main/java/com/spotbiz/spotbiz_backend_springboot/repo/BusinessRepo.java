package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface BusinessRepo extends JpaRepository<Business, Integer> {
    Business findByUserUserId(Integer userId);

    List<Business> findByStatus(String status);

//    @Query("SELECT c.business FROM BusinessCategory c WHERE c.tags LIKE %:keyword%")
//    List<Business> findByAnyTag(@Param("keyword") String keyword);

//    @Query(value = "SELECT c.business FROM BusinessCategory c WHERE c.tags @> array[:tags]")
//    List<Business> findByTags(@Param("tags") List<String> tags);

//    @Query(value = "SELECT * FROM BusinessCategory c WHERE c.tags ?| array[:tags]", nativeQuery = true)
//    List<Business> findByAnyTag(@Param("tags") List<String> tags);

//    @Query(value = "SELECT * FROM business_category c WHERE c.tags ?| :tags", nativeQuery = true)
//    List<Business> findByAnyTag(@Param("tags") List<String> tags);

    @Query("SELECT b FROM BusinessCategory b WHERE b.tags IN :tags")
    List<Business> findByAnyTag(@Param("tags") List<String> tags);



}
