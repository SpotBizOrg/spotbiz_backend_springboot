package com.spotbiz.spotbiz_backend_springboot.component;

import com.spotbiz.spotbiz_backend_springboot.entity.SubscriptionBilling;
import com.spotbiz.spotbiz_backend_springboot.repo.SubscriptionBillingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class SubscriptionBillingScheduler {

    @Autowired
    private SubscriptionBillingRepo subscriptionBillingRepo;

    // Runs daily at midnight
    @Scheduled(cron = "0 0 0 * * ?")
    public void deactivateExpiredSubscriptions() {
        checkAndDeactivateExpiredSubscriptions();
    }

    // Runs on application startup
    @PostConstruct
    public void runOnStartup() {
        checkAndDeactivateExpiredSubscriptions();
    }

    private void checkAndDeactivateExpiredSubscriptions() {
        List<SubscriptionBilling> activeSubscriptions = subscriptionBillingRepo.findByIsActiveTrueAndBillingStatus("PAID");
        LocalDateTime now = LocalDateTime.now();

        for (SubscriptionBilling subscription : activeSubscriptions) {
            if (subscription.getBillingDate().plusDays(30).isBefore(now)) {
                subscription.setIsActive(false);
                subscriptionBillingRepo.save(subscription);
            }
        }

        System.out.println("Checked and updated subscriptions at " + now);
    }
}
