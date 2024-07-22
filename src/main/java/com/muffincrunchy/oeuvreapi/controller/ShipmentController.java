package com.muffincrunchy.oeuvreapi.controller;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateShipmentRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateShipmentRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.CommonResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Shipment;
import com.muffincrunchy.oeuvreapi.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.ID_PATH;
import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.SHIP_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(SHIP_URL)
public class ShipmentController {

    private final ShipmentService shipmentService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<Shipment>>> getShipment() {
        List<Shipment> shipments = shipmentService.getAll();
        CommonResponse<List<Shipment>> response = CommonResponse.<List<Shipment>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(shipments)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<CommonResponse<Shipment>> getShipmentById(@PathVariable("id") String id) {
        Shipment shipment = shipmentService.getById(id);
        CommonResponse<Shipment> response = CommonResponse.<Shipment>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(shipment)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<Shipment>> createShipment(@RequestBody CreateShipmentRequest request) {
        Shipment shipment = shipmentService.create(request);
        CommonResponse<Shipment> response = CommonResponse.<Shipment>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success save data")
                .data(shipment)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<Shipment>> updateShipment(@RequestBody UpdateShipmentRequest request) {
        Shipment shipment = shipmentService.update(request);
        CommonResponse<Shipment> response = CommonResponse.<Shipment>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success update data")
                .data(shipment)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = ID_PATH)
    public ResponseEntity<CommonResponse<String>> deleteShipmentById(@PathVariable("id") String id) {
        shipmentService.delete(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success delete data")
                .build();
        return ResponseEntity.ok(response);
    }
}
