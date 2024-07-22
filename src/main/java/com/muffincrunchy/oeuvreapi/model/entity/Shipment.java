package com.muffincrunchy.oeuvreapi.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shipments")
public class Shipment {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "fee")
    private Long fee;
}
