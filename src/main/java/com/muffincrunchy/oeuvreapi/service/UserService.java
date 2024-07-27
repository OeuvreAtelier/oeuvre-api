package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.dto.request.PagingRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.SearchArtistRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateUserRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.UserResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Store;
import com.muffincrunchy.oeuvreapi.model.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    List<UserResponse> getAll();
    Page<UserResponse> searchArtist(PagingRequest pagingRequest, SearchArtistRequest request);
    User getById(String id);
    UserResponse getResponseById(String id);
    User create(User request);
    UserResponse update(UpdateUserRequest request);
    void delete(String id);
    User getByUserAccountId(String userAccountId);
    void updateArtistStatusById(String id, Boolean isArtist);
    void saveStore(Store store);
}