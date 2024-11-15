package com.spotbiz.spotbiz_backend_springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscription_billing")
public class SubscriptionBilling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_billing_id")
    private Integer subscriptionBillingId;

    @ManyToOne
    @JoinColumn(name = "id")
    private Package pkg;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    @Column(name = "billing_date", columnDefinition = "TIMESTAMPTZ")
    private LocalDateTime billingDate = LocalDateTime.now();

    @Column(name = "billing_status")
    private String billingStatus;

    private Double amount;
}
