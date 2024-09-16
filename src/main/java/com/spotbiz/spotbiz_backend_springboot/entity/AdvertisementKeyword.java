package com.spotbiz.spotbiz_backend_springboot.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Getter
@Setter
@Entity

@Table(name = "advertiesment_keyword")
public class AdvertisementKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @OneToOne
    @JoinColumn(name = "advertisement_id" , referencedColumnName = "ads_Id")
    private Advertisement Advertisement;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private String tags;

}
