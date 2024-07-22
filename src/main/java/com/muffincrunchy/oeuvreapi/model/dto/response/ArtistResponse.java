package com.muffincrunchy.oeuvreapi.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ArtistResponse {

    private String id;
    private String name;
    private Date birthDate;
    private String phoneNo;
    private String email;
    private String userAccountId;
}
