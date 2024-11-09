package com.spotbiz.spotbiz_backend_springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "reported_business")
public class ReportedBusiness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="report_id")
    private Integer reportId;

    @Column(nullable = false)
    private String reason;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String status;
}
