package com.spotbiz.spotbiz_backend_springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementRecommendationDto;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Setter
@Entity
@Table(name = "Advertisement")

public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ads_id")
    private Integer adsId;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private String data;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "business_id")
    private Business business;

    @Column
    private Boolean status;

    @Override
    public String toString() {
        return "Advertisement{" +
                "adsId=" + adsId +
                ", data='" + data + '\'' +
                ", business=" + (business != null ? business.getBusinessId() : "null") +
                ", status=" + status +
                '}';
    }
}
