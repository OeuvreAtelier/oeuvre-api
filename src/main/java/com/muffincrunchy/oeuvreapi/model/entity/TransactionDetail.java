package com.muffincrunchy.oeuvreapi.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transaction_details")
public class TransactionDetail {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "trans_id", nullable = false)
    @JsonBackReference
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "merch_id", nullable = false)
    private Merch merch;

    @ManyToOne
    @JoinColumn(name = "shipment_id", nullable = false)
    private Shipment shipment;

    @Column(name = "merch_price", updatable = false, nullable = false)
    private Long merchPrice;

    @Column(name = "shipment_fee", updatable = false, nullable = false)
    private Long shipmentFee;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;
}
