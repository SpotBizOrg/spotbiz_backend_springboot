package com.spotbiz.spotbiz_backend_springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NaturalId
    private Category category;

    public Business(String businessName, String businessRegNo, String status, User user){
        this.name = businessName;
        this.businessRegNo=businessRegNo;
        this.status=status;
        this.user = user;
    }


}
