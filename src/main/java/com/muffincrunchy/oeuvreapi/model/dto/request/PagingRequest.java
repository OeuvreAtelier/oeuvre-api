package com.muffincrunchy.oeuvreapi.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagingRequest {

    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
}
