package com.spotbiz.spotbiz_backend_springboot.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Map;
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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
//    private String tags;
    private String tags;

    public BusinessCategory( Category category, String tags, Business business) {
        this.category = category;
        this.tags = tags;
        this.business = business;
    }

    public BusinessCategory() {

    }


}
