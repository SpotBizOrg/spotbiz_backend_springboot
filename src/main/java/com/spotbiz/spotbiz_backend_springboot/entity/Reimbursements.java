package com.spotbiz.spotbiz_backend_springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reimbursment")
public class Reimbursements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reimbursment_id")
    private int Id;

    @ManyToOne
    @JoinColumn(name = "business_id", referencedColumnName = "business_id")
    private Business business;

    @Column(name="date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    @Column(name="amount")
    private float amount;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private ReimbursementStatus status = ReimbursementStatus.PENDING;

}
