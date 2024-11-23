package com.spotbiz.spotbiz_backend_springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "business_clicks")
public class BusinessClicks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer businessClickId;

    @OneToOne
    @JoinColumn(name = "business_id")
    private Business business;
    private Integer clicks;
}
