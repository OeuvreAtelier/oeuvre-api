package com.muffincrunchy.oeuvreapi.controller;

import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateArtistRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.ArtistResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.CommonResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Artist;
import com.muffincrunchy.oeuvreapi.service.ArtistService;
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
public class ArtistController {

    private final ArtistService artistService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<ArtistResponse>>> getMerch() {
        List<ArtistResponse> artists = artistService.getAll();
        CommonResponse<List<ArtistResponse>> response = CommonResponse.<List<ArtistResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(artists)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<CommonResponse<Artist>> getArtistById(@PathVariable("id") String id) {
        Artist artist = artistService.getById(id);
        CommonResponse<Artist> response = CommonResponse.<Artist>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(artist)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<CommonResponse<Artist>> getArtistByUserId(@PathVariable("user_id") String userId) {
        Artist artist = artistService.getByUserId(userId);
        CommonResponse<Artist> response = CommonResponse.<Artist>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(artist)
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
    public ResponseEntity<CommonResponse<ArtistResponse>> updateArtist(@RequestBody UpdateArtistRequest request) {
        ArtistResponse artist = artistService.update(request);
        CommonResponse<ArtistResponse> response = CommonResponse.<ArtistResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success update data")
                .data(artist)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = ID_PATH)
    public ResponseEntity<CommonResponse<String>> deleteArtistById(@PathVariable("id") String id) {
        artistService.delete(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success delete data")
                .build();
        return ResponseEntity.ok(response);
    }
}
