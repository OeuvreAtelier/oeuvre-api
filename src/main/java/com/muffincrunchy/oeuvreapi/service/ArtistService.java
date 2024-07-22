package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateArtistRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.ArtistResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Artist;

import java.util.List;

public interface ArtistService {

    List<ArtistResponse> getAll();
    Artist getById(String id);
    Artist create(Artist request);
    ArtistResponse update(UpdateArtistRequest request);
    void delete(String id);
    Artist getByUserId(String userId);
}
