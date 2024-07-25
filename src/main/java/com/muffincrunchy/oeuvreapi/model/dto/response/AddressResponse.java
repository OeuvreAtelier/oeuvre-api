package com.muffincrunchy.oeuvreapi.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AddressResponse {

    private String id;
    private UserResponse user;
    private String country;
    private String state;
    private String city;
    private String detail;
    private String postalCode;
    private String phoneNumber;
    private Date createdAt;
    private Date updatedAt;
}
