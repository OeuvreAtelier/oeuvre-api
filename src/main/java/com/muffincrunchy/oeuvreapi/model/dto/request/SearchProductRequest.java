package com.muffincrunchy.oeuvreapi.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchProductRequest {

    private String name;
    private String category;
    private String type;
}
