package com.muffincrunchy.oeuvreapi.controller;

import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateUserRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.UserResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.CommonResponse;
import com.muffincrunchy.oeuvreapi.model.entity.User;
import com.muffincrunchy.oeuvreapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.ARTIST_URL;
import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.ID_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(ARTIST_URL)
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<UserResponse>>> getMerch() {
        List<UserResponse> artists = userService.getAll();
        CommonResponse<List<UserResponse>> response = CommonResponse.<List<UserResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(artists)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<CommonResponse<User>> getArtistById(@PathVariable("id") String id) {
        User user = userService.getById(id);
        CommonResponse<User> response = CommonResponse.<User>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(user)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<CommonResponse<User>> getArtistByUserId(@PathVariable("user_id") String userId) {
        User user = userService.getByUserAccountId(userId);
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
    public ResponseEntity<CommonResponse<UserResponse>> updateArtist(@RequestBody UpdateUserRequest request) {
        UserResponse artist = userService.update(request);
        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success update data")
                .data(artist)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = ID_PATH)
    public ResponseEntity<CommonResponse<String>> deleteArtistById(@PathVariable("id") String id) {
        userService.delete(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success delete data")
                .build();
        return ResponseEntity.ok(response);
    }
}
