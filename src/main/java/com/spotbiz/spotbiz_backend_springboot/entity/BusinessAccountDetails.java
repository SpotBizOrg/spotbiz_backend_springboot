package com.spotbiz.spotbiz_backend_springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "business_card_details")
public class BusinessAccountDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "business_id", referencedColumnName = "business_id")
    private Business business;

    @Column(name="account_no")
    private String accountNo;

    @Column(name="account_holder_name")
    private String accountHolderName;

    @Column(name="bank_name")
    private String bankName;

    @Column(name="branch_name")
    private String branchName;

    @Column(name="phone_no")
    private String phoneNo;

    @Column(name="email")
    private String email;

}
