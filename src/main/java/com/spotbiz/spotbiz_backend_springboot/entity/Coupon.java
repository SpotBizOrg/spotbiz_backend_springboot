package com.spotbiz.spotbiz_backend_springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="coupon_id")
    private int couponId;

    @Column(name="date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    @Column(name="description")
    private String description;

    @Column(name="discount")
    private float discount;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private CouponStatus status = CouponStatus.PENDING;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="business_id", referencedColumnName = "business_id")
    private Business business;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;

}
