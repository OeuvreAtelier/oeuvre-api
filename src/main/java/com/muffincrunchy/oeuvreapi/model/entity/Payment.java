package com.muffincrunchy.oeuvreapi.model.entity;

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

    @Column(name = "token")
    private String token;

    @Column(name = "redirect_url")
    private String redirectUrl;

    @Column(name = "transaction_status")
    private String transactionStatus;
}
