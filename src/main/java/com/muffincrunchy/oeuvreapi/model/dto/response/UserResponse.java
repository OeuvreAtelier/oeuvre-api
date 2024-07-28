package com.muffincrunchy.oeuvreapi.model.dto.response;

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
    private String gender;
    private Date birthDate;
    private String phoneNumber;
    private boolean isArtist;
    private StoreResponse store;
    private String userAccountId;
    private Date createdAt;
    private Date updatedAt;
    private ImageResponse imagePicture;
    private ImageResponse imageBanner;
}
