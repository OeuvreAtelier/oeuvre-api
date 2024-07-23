package com.muffincrunchy.oeuvreapi.model.dto.response;

import com.muffincrunchy.oeuvreapi.model.entity.Gender;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String displayName;
    private String email;
    private Gender gender;
    private Date birthDate;
    private String phoneNumber;
    private boolean isArtist;
    private String userAccountId;
}
