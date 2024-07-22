package com.muffincrunchy.oeuvreapi.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "merchs")
public class Merch {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "price")
    private Long price;

    @Column(name = "stock")
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Payment> payments;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Shipment> shipments;
}
