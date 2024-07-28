package com.spotbiz.spotbiz_backend_springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.Optional;

@Getter
@Setter
@Entity
@Table(name = "business_category")
public class BusinessCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer businessCategoryId;

    @OneToOne
    @JoinColumn(name = "category_id")
    @NaturalId
    private Category category;

    @OneToOne
    @JoinColumn(name = "business_id")
    @NaturalId
    private Business business;

    private String tags;

    public BusinessCategory( Category category, String tags, Business business) {
        this.category = category;
        this.tags = tags;
        this.business = business;
    }

    public BusinessCategory() {

    }


}
