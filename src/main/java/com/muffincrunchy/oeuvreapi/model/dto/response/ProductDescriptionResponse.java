package com.muffincrunchy.oeuvreapi.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ProductDescriptionResponse {

    private String id;
    private String description;
    private Date createdAt;
    private Date updatedAt;
}
