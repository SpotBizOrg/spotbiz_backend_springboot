package com.spotbiz.spotbiz_backend_springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "encoded_coupon")

public class EncodedCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "coupon_id")
    private int coupon_id;

    @Column(name = "discount")
    private double discount;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private CouponStatus status = CouponStatus.PENDING;

    @Column(name = "encoded_coupon_id")
    private String encodedCouponId;
}
