package com.spotbiz.spotbiz_backend_springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_pics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pic_id")
    private Integer picId;
    @Column(name = "image_url")
    private String imageUrl;
}
