package com.spotbiz.spotbiz_backend_springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Advertisement")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ads_id")
    private Integer adsId;

    @Column(columnDefinition = "jsonb")
    private String data;
    // sample data : {"date": "2024-07-10", "time": "15:00:00", "image": "image3.jpg", "description": "Buy One Get One Free Offer!"}
    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

}
