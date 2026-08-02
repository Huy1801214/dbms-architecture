package store_api.controller;

import lombok.RequiredArgsConstructor;
import store_api.dto.request.CreateCustomerRequest;
import store_api.dto.response.CustomerPageResponse;
import store_api.model.customer.Customer;
import store_api.service.CustomerService;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<CustomerPageResponse> getCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(
                customerService.getCustomers(page, size));
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        Customer cus = customerService.createCustomer(request);
        URI location = URI.create("/api/v1/customers/" + cus.getId());
        return ResponseEntity.created(location).body(cus);
    }

    @GetMapping("/search")
    public ResponseEntity<CustomerPageResponse> searchCustomers(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(customerService.searchCustomers(keyword, page, size));
    }
}
