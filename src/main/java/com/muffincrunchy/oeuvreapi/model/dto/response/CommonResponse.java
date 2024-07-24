package com.muffincrunchy.oeuvreapi.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponse<T> {

    private Integer statusCode;
    private String message;
    private T data;
    private PagingResponse paging;
}
