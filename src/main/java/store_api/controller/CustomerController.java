package store_api.controller;

import lombok.RequiredArgsConstructor;
import store_api.dto.request.BatchGetCustomersRequest;
import store_api.dto.request.CreateCustomerRequest;
import store_api.dto.request.UpdateUserRoleRequest;
import store_api.dto.response.CountResponse;
import store_api.dto.response.CustomerPageResponse;
import store_api.model.customer.Customer;
import store_api.model.customer.CustomerStatus;
import store_api.model.customer.CustomerUser;
import store_api.service.CustomerService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/filter")
    public ResponseEntity<CustomerPageResponse> filterCustomers(
            @RequestParam(required = false) CustomerStatus status,

            @RequestParam(required = false) String category,

            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdFrom,

            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdTo,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(
                customerService.filterCustomers(status, category, createdFrom, createdTo, page, size));
    }

    @GetMapping("/count")
    public ResponseEntity<CountResponse> countCustomer(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) CustomerStatus status,
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(
                new CountResponse(customerService.countCustomer(keyword, status, category)));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(
            @PathVariable String customerId) {
        Customer customer = customerService.getCustomerById(customerId);

        return ResponseEntity.ok(customer);
    }

    @PatchMapping("/{customerId}/users/{userId}/role")
    public ResponseEntity<CustomerUser> updateCustomerUserRole(
            @PathVariable String customerId,
            @PathVariable String userId,
            @Valid @RequestBody UpdateUserRoleRequest request) {
        return ResponseEntity.ok(
                customerService.updateCustomerUserRole(
                        customerId,
                        userId,
                        request));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Customer>> getCustomersBatch(
            @Valid @RequestBody BatchGetCustomersRequest request) {
        return ResponseEntity.ok(customerService.getCustomersBatch(request));
    }
}
