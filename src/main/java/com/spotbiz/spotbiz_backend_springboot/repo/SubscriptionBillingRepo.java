package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.SubscriptionBilling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface SubscriptionBillingRepo extends JpaRepository<SubscriptionBilling, Integer> {

    SubscriptionBilling findBySubscriptionBillingId(int subscriptionBillingId);

    boolean existsBySubscriptionBillingId(int subscriptionBillingId);

    void deleteBySubscriptionBillingId(int subscriptionBillingId);

    List<SubscriptionBilling> findByIsActiveTrueAndBillingStatus(String billingStatus);

//    @Query("SELECT sb FROM SubscriptionBilling sb " +
//            "WHERE sb.business.businessId = :businessId " +
//            "AND sb.isActive = true AND sb.billingStatus = 'PAID' " +
//            "ORDER BY sb.billingDate DESC")
//    Optional<SubscriptionBilling> findByBusinessBusinessId(@Param("businessId") Integer businessId);

    Optional<SubscriptionBilling> findFirstByBusinessBusinessIdAndIsActiveTrueAndBillingStatusOrderByBillingDateDesc(
            Integer businessId, String billingStatus);

    @Query("SELECT sum(s.amount) FROM SubscriptionBilling s WHERE s.billingStatus= :billingStatus")
    Double getTotalBillings(@Param("billingStatus") String billingStatus);

    List<SubscriptionBilling> findAllByBillingStatusAndBillingDateAfter(String billingStatus, LocalDateTime billingDate);

}
