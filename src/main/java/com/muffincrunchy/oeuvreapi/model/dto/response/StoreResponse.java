package com.muffincrunchy.oeuvreapi.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class StoreResponse {

    private String id;
    private AddressStoreResponse address;
    private String description;
    private String pixiv;
    private String twitter;
    private Date createdAt;
    private Date updatedAt;
}
