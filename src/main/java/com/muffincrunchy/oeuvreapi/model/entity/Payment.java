package com.muffincrunchy.oeuvreapi.model.entity;

import com.muffincrunchy.oeuvreapi.model.constant.PaymentType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "payment")
    @Enumerated(EnumType.STRING)
    private PaymentType payment;
}
