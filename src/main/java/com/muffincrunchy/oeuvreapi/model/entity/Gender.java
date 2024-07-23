package com.muffincrunchy.oeuvreapi.model.entity;

import com.muffincrunchy.oeuvreapi.model.constant.UserGender;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "genders")
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private UserGender gender;
}
