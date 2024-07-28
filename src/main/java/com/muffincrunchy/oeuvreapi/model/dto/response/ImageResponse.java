package com.muffincrunchy.oeuvreapi.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageResponse {
    private String path;
    private String name;
}
