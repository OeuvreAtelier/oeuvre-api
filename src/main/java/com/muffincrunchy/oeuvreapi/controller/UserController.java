package com.muffincrunchy.oeuvreapi.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muffincrunchy.oeuvreapi.model.dto.request.PagingRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.SearchArtistRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateUserRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.PagingResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.UserResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.CommonResponse;
import com.muffincrunchy.oeuvreapi.model.entity.User;
import com.muffincrunchy.oeuvreapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(USER_URL)
public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<UserResponse>>> getUser() {
        List<UserResponse> artists = userService.getAll();
        CommonResponse<List<UserResponse>> response = CommonResponse.<List<UserResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(artists)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(SEARCH_PATH)
    public ResponseEntity<CommonResponse<List<UserResponse>>> searchArtist(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "24") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "displayName") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "displayName", required = false) String displayName
    ) {
        SearchArtistRequest request = SearchArtistRequest.builder()
                .displayName(displayName)
                .build();
        PagingRequest pagingRequest = PagingRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<UserResponse> users = userService.searchArtist(pagingRequest, request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .page(users.getPageable().getPageNumber() + 1)
                .size(users.getPageable().getPageSize())
                .hasNext(users.hasNext())
                .hasPrevious(users.hasPrevious())
                .build();
        CommonResponse<List<UserResponse>> response = CommonResponse.<List<UserResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(users.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<CommonResponse<UserResponse>> getUserById(@PathVariable("id") String id) {
        UserResponse user = userService.getResponseById(id);
        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(user)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{user_account_id}")
    public ResponseEntity<CommonResponse<User>> getUserByUserAccountId(@PathVariable("user_account_id") String userAccountId) {
        User user = userService.getByUserAccountId(userAccountId);
        CommonResponse<User> response = CommonResponse.<User>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(user)
                .build();
        return ResponseEntity.ok(response);
    }

//    @PostMapping
//    public ResponseEntity<CommonResponse<Artist>> createArtist(@RequestBody CreateArtistRequest request) {
//        Artist artist = artistService.create(request);
//        CommonResponse<Artist> response = CommonResponse.<Artist>builder()
//                .statusCode(HttpStatus.OK.value())
//                .message("success save data")
//                .data(artist)
//                .build();
//        return ResponseEntity.ok(response);
//    }

    @PutMapping
    public ResponseEntity<CommonResponse<?>> updateUser(@RequestPart(name = "user") String user, @RequestPart(name = "image", required = false) MultipartFile image) {
        CommonResponse.CommonResponseBuilder<UserResponse> responseBuilder = CommonResponse.builder();
        try {
            UpdateUserRequest request = objectMapper.readValue(user, new TypeReference<>() {
            });
            request.setImage(image);
            UserResponse response = userService.update(request);
            responseBuilder.statusCode(HttpStatus.CREATED.value());
            responseBuilder.message("success save data");
            responseBuilder.data(response);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBuilder.build());
        } catch (Exception e) {
            responseBuilder.message("internal server error");
            responseBuilder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBuilder.build());
        }
    }

//    @DeleteMapping(value = ID_PATH)
//    public ResponseEntity<CommonResponse<String>> deleteArtistById(@PathVariable("id") String id) {
//        userService.delete(id);
//        CommonResponse<String> response = CommonResponse.<String>builder()
//                .statusCode(HttpStatus.OK.value())
//                .message("success delete data")
//                .build();
//        return ResponseEntity.ok(response);
//    }
}