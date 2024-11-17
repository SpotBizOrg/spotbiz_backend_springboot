package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.SubscriptionBilling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionBillingRepo extends JpaRepository<SubscriptionBilling, Integer> {

    SubscriptionBilling findBySubscriptionBillingId(int subscriptionBillingId);

    boolean existsBySubscriptionBillingId(int subscriptionBillingId);

    void deleteBySubscriptionBillingId(int subscriptionBillingId);

    List<SubscriptionBilling> findByIsActiveTrue();

//    @Query(value = "SELECT * FROM subscription_billings ORDER BY id ASC", nativeQuery = true)
//    List<SubscriptionBilling> findAllOrderedBySubscriptionBillingIdAsc();
}
