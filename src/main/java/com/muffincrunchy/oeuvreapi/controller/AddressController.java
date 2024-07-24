package com.muffincrunchy.oeuvreapi.controller;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateAddressRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateAddressRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.AddressResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.CommonResponse;
import com.muffincrunchy.oeuvreapi.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.ADDRESS_URL;
import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.ID_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(ADDRESS_URL)
public class AddressController {

    private final AddressService addressService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<AddressResponse>>> getAddresses() {
        List<AddressResponse> address = addressService.getAll();
        CommonResponse<List<AddressResponse>> response = CommonResponse.<List<AddressResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(address)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<CommonResponse<List<AddressResponse>>> getAddressByUserId(@PathVariable("user_id") String userId) {
        List<AddressResponse> address = addressService.getByUserId(userId);
        CommonResponse<List<AddressResponse>> response = CommonResponse.<List<AddressResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(address)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<CommonResponse<AddressResponse>> getAddressResponseById(@PathVariable("id") String id) {
        AddressResponse address = addressService.getResponseById(id);
        CommonResponse<AddressResponse> response = CommonResponse.<AddressResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(address)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<AddressResponse>> createAddress(@RequestBody CreateAddressRequest request) {
        AddressResponse artist = addressService.create(request);
        CommonResponse<AddressResponse> response = CommonResponse.<AddressResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success update data")
                .data(artist)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<AddressResponse>> updateAddress(@RequestBody UpdateAddressRequest request) {
        AddressResponse artist = addressService.update(request);
        CommonResponse<AddressResponse> response = CommonResponse.<AddressResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success update data")
                .data(artist)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = ID_PATH)
    public ResponseEntity<CommonResponse<String>> deleteArtistById(@PathVariable("id") String id) {
        addressService.delete(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success delete data")
                .build();
        return ResponseEntity.ok(response);
    }
}
