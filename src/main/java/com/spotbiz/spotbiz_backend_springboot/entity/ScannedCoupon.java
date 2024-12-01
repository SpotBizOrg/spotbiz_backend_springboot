package com.spotbiz.spotbiz_backend_springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "scanned_coupon")
public class ScannedCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="scanned_coupon_id")
    private int scannedCouponId;

    @Column(name="coupon_id")
    private int couponId;

    @Column(name="date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    @Column(name="business_id")
    private int businessId;

    @Column(name="discount")
    private float discount;

    @Column(name="bill_image")
    private String billImage;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private ScannedCouponStatus status = ScannedCouponStatus.PENDING;

}
