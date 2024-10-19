package com.spotbiz.spotbiz_backend_springboot.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "business_social_links")
public class BusinessSocialLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_id", nullable = false)
    private Integer businessId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SocialLinkType type;

    @Column(name = "link", nullable = false)
    private String link;


}