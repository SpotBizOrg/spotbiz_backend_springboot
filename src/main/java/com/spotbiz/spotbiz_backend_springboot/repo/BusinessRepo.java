package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDashboardDto;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDataDto;
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
            "JOIN subscription_billing s ON b.business_id = s.business_id " +
            "WHERE EXISTS ( " +
            "    SELECT 1 FROM jsonb_array_elements_text(bc.tags->'keywords') tag " +
            "    WHERE tag = ANY (cast(:tags as text[])))" +
            "AND s.billing_status = 'PAID' AND s.is_active = true "+
            "AND b.status = 'APPROVED' " +
            "ORDER BY s.id DESC", nativeQuery = true)
//    List<Business> findByAnyTag(@Param("tags") String[] tags);
    Page<Business> findByAnyTag(@Param("tags") String[] tags, Pageable pageable);

    @Query(value ="SELECT tags  FROM business_category WHERE business_id = :businessId", nativeQuery = true)
    String getBusinessCategory(@Param("businessId") Integer businessId);

    @Query(value = "SELECT b.business_id, b.business_reg_no, b.name, b.address, b.contact_no, b.description, b.location_url, b.logo, b.profile_cover, b.status, b.user_id "+
            "FROM business b " +
            "JOIN business_category bc ON b.business_id = bc.business_id "+
            "JOIN subscription_billing s ON b.business_id = s.business_id "+
            "WHERE "+
            "bc.category_id = :categoryId "+
            "AND s.billing_status = 'PAID' AND s.is_active = true "+
            "AND b.status = 'APPROVED' "+
            "ORDER BY s.id DESC", nativeQuery = true)
    Page<Business> findByCategory(@Param("categoryId") Integer categoryId, Pageable pageable);

//    @Query(value = "SELECT b.business_id AS businessId, b.logo AS logo, c.clicks AS clickCount, COUNT(s.user_id) AS subscriberCount, sp.id AS packageId FROM business b \n" +
//            "JOIN business_clicks c ON b.business_id = c.business_id \n" +
//            "JOIN subscribe s ON b.business_id = s.business_id\n" +
//            "JOIN subscription_billing sp ON sp.business_id=b.business_id\n" +
//            "WHERE b.business_id = :businessId \n" +
//            "GROUP BY b.business_id, b.logo, c.clicks, sp.id", nativeQuery = true)
//    BusinessDashboardDto getData(@Param("businessId") Integer businessId);

    @Query(value = "SELECT b.business_id AS businessId, b.logo AS logo, c.clicks AS clickCount, COUNT(s.user_id) AS subscriberCount, sp.id AS packageId FROM business b \n" +
            "JOIN business_clicks c ON b.business_id = c.business_id \n" +
            "JOIN subscribe s ON b.business_id = s.business_id\n" +
            "JOIN subscription_billing sp ON sp.business_id=b.business_id\n" +
            "WHERE b.business_id = :businessId \n" +
            "GROUP BY b.business_id, b.logo, c.clicks, sp.id", nativeQuery = true)
    List<Object[]> getDataAsObjectArray(@Param("businessId") Integer businessId);

    @Query(value = "SELECT logo FROM business WHERE business_id=:businessId", nativeQuery = true)
    String getBusinessLogo(@Param("businessId") Integer businessId);
}
