package com.spotbiz.spotbiz_backend_springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

@Getter
@Setter
@Entity
@Table(name = "business")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_id")
    private Integer businessId;

    @Column(name = "business_reg_no")
    private String businessRegNo;

    private String name;
    private String logo;

    @Column(name = "profile_cover")
    private String profileCover;
    private String description;

    @Column(name = "location_url")
    private String locationUrl;

    @Column(name = "contact_no")
    private String contactNo;

    private String address;
    private String status;

    @OneToOne
    @JoinColumn(name = "user_id")
    @NaturalId
    private User user;




}
