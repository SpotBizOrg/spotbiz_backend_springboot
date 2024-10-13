package com.spotbiz.spotbiz_backend_springboot.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import java.util.Optional;

@Getter
@Setter
@Entity
@Table(name = "business_opening_hours")
public class BusinessOpeningHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private String openingHours;

    @OneToOne
    @JoinColumn(name = "business_id")
    private Business business;
}
