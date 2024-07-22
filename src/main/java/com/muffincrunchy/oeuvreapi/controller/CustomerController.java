package com.muffincrunchy.oeuvreapi.controller;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateCustomerRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateCustomerRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.CommonResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Customer;
import com.muffincrunchy.oeuvreapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.CUSTOMER_URL;
import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.ID_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(CUSTOMER_URL)
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<Customer>>> getCustomer() {
        List<Customer> customers = customerService.getAll();
        CommonResponse<List<Customer>> response = CommonResponse.<List<Customer>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(customers)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<CommonResponse<Customer>> getCustomerById(@PathVariable("id") String id) {
        Customer customer = customerService.getById(id);
        CommonResponse<Customer> response = CommonResponse.<Customer>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(customer)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<Customer>> createCustomer(@RequestBody CreateCustomerRequest request) {
        Customer customer = customerService.create(request);
        CommonResponse<Customer> response = CommonResponse.<Customer>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success save data")
                .data(customer)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<Customer>> updateCustomer(@RequestBody UpdateCustomerRequest request) {
        Customer customer = customerService.update(request);
        CommonResponse<Customer> response = CommonResponse.<Customer>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success update data")
                .data(customer)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = ID_PATH)
    public ResponseEntity<CommonResponse<String>> deleteCustomerById(@PathVariable("id") String id) {
        customerService.delete(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success delete data")
                .build();
        return ResponseEntity.ok(response);
    }
}
