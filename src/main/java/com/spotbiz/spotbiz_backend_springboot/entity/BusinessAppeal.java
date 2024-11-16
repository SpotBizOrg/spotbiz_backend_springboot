package com.spotbiz.spotbiz_backend_springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "business_appeal")
public class BusinessAppeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="appeal_id")
    private Integer appealId;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String status;

    @OneToOne
    @JoinColumn(name = "business_id", nullable = false, unique = true)
    private Business business;

    @OneToOne
    @JoinColumn(name = "report_id", nullable = false, unique = true)
    private ReportedBusiness reportedBusiness;
}
