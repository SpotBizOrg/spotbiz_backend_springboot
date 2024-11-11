package com.spotbiz.spotbiz_backend_springboot.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "packages")
public class Package {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer packageId;

    @Column(name = "feature", nullable = false)
    private String feature;

    @Column(name = "ads_per_week")
    private Integer adsPerWeek;

    @Column(name = "analytics")
    private Boolean analytics;

    @Column(name = "fake_reviews")
    private Boolean fakeReviews;

    @Column(name = "recommendation")
    private Boolean recommendation;

    @Column(name = "messaging")
    private Boolean messaging;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "display_business_details")
    private String listing;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "pkg")
    private List<Business> businesses;

}
