package com.muffincrunchy.oeuvreapi.controller;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateStoreRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateStoreRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.AddressResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.CommonResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.StoreResponse;
import com.muffincrunchy.oeuvreapi.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.ID_PATH;
import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.STORE_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(STORE_URL)
public class StoreController {

    private final StoreService storeService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<StoreResponse>>> getStores() {
        List<StoreResponse> stores = storeService.getAll();
        CommonResponse<List<StoreResponse>> response = CommonResponse.<List<StoreResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(stores)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<CommonResponse<StoreResponse>> getStoreById(@PathVariable("id") String id) {
        StoreResponse store = storeService.getResponseById(id);
        CommonResponse<StoreResponse> response = CommonResponse.<StoreResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(store)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<CommonResponse<StoreResponse>> getStoreByUserId(@PathVariable("user_id") String userId) {
        StoreResponse store = storeService.getByUserId(userId);
        CommonResponse<StoreResponse> response = CommonResponse.<StoreResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(store)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<StoreResponse>> createStore(@RequestBody CreateStoreRequest request) {
        StoreResponse store = storeService.create(request);
        CommonResponse<StoreResponse> response = CommonResponse.<StoreResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(store)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<StoreResponse>> updateStore(@RequestBody UpdateStoreRequest request) {
        StoreResponse store = storeService.update(request);
        CommonResponse<StoreResponse> response = CommonResponse.<StoreResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(store)
                .build();
        return ResponseEntity.ok(response);
    }
}
