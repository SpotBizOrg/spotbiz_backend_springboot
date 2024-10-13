package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface BusinessRepo extends JpaRepository<Business, Integer> {
    Business findByUserUserId(Integer userId);

    List<Business> findByStatus(String status);
    Business findByBusinessId(int businessId);

//    @Query("SELECT c.business FROM BusinessCategory c WHERE c.tags LIKE %:keyword%")
//    List<Business> findByAnyTag(@Param("keyword") String keyword);

//    @Query(value = "SELECT c.business FROM BusinessCategory c WHERE c.tags @> array[:tags]")
//    List<Business> findByTags(@Param("tags") List<String> tags);

//    @Query(value = "SELECT * FROM BusinessCategory c WHERE c.tags ?| array[:tags]", nativeQuery = true)
//    List<Business> findByAnyTag(@Param("tags") List<String> tags);

//    @Query(value = "SELECT * FROM business_category c WHERE c.tags ?| :tags", nativeQuery = true)
//    List<Business> findByAnyTag(@Param("tags") List<String> tags);

//    @Query("SELECT b FROM BusinessCategory b WHERE b.tags IN :tags")
//    List<Business> findByAnyTag(@Param("tags") String tags);

    @Query(value = "SELECT b.business_id, b.business_reg_no, b.name, b.address, b.contact_no, b.description, b.location_url, b.logo, b.profile_cover, b.status, b.user_id " +
            "FROM business_category bc " +
            "JOIN business b ON b.business_id = bc.business_id " +
            "WHERE EXISTS ( " +
            "    SELECT 1 FROM jsonb_array_elements_text(bc.tags->'keywords') tag " +
            "    WHERE tag = ANY (cast(:tags as text[])) " +
            ")", nativeQuery = true)
//    List<Business> findByAnyTag(@Param("tags") String[] tags);
    Page<Business> findByAnyTag(@Param("tags") String[] tags, Pageable pageable);

    @Query(value ="SELECT tags  FROM business_category WHERE business_id = :businessId", nativeQuery = true)
    String getBusinessCategory(@Param("businessId") Integer businessId);

    @Query(value = "SELECT b.business_id, b.business_reg_no, b.name, b.address, b.contact_no, b.description, b.location_url, b.logo, b.profile_cover, b.status, b.user_id  FROM business b, business_category bc WHERE b.business_id = bc.business_id AND bc.category_id = :categoryId AND b.status = 'APPROVED'", nativeQuery = true)
    Page<Business> findByCategory(@Param("categoryId") Integer categoryId, Pageable pageable);
}
