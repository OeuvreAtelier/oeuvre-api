package com.muffincrunchy.oeuvreapi.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {

    private String id;
    private String name;
    private Long price;
    private Integer stock;
    private UserResponse user;
    private String category;
    private String type;
}
