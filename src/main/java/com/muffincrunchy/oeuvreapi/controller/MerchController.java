package com.muffincrunchy.oeuvreapi.controller;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateMerchRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateMerchRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.CommonResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Merch;
import com.muffincrunchy.oeuvreapi.service.MerchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(MERCH_URL)
public class MerchController {

    private final MerchService merchService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<Merch>>> getMerch() {
        List<Merch> merchs = merchService.getAll();
        CommonResponse<List<Merch>> response = CommonResponse.<List<Merch>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(merchs)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<CommonResponse<Merch>> getMerchById(@PathVariable("id") String id) {
        Merch merch = merchService.getById(id);
        CommonResponse<Merch> response = CommonResponse.<Merch>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(merch)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<Merch>> createMerch(@RequestBody CreateMerchRequest request) {
        Merch merch = merchService.create(request);
        CommonResponse<Merch> response = CommonResponse.<Merch>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success save data")
                .data(merch)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<Merch>> updateMerch(@RequestBody UpdateMerchRequest request) {
        Merch merch = merchService.update(request);
        CommonResponse<Merch> response = CommonResponse.<Merch>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success update data")
                .data(merch)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = ID_PATH)
    public ResponseEntity<CommonResponse<String>> deleteMerchById(@PathVariable("id") String id) {
        merchService.delete(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success delete data")
                .build();
        return ResponseEntity.ok(response);
    }
}
