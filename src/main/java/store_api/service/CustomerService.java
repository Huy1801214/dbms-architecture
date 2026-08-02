package store_api.service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import store_api.dto.request.BatchArchiveCustomersRequest;
import store_api.dto.request.BatchDeleteCustomersRequest;
import store_api.dto.request.BatchGetCustomersRequest;
import store_api.dto.request.BatchRestoreCustomersRequest;
import store_api.dto.request.BatchUpdateCustomerStatusRequest;
import store_api.dto.request.CreateCustomerRequest;
import store_api.dto.request.UpdateUserRoleRequest;
import store_api.dto.response.BatchOperationResponse;
import store_api.dto.response.CustomerPageResponse;
import store_api.exception.CustomerDomainAlreadyExistsException;
import store_api.exception.CustomerNotFoundException;
import store_api.exception.CustomerUserNotFoundException;
import store_api.model.customer.Customer;
import store_api.model.customer.CustomerStatus;
import store_api.model.customer.CustomerUser;
import store_api.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {
        private final CustomerRepository customerRepository;

        public List<Customer> getCustomersBatch(BatchGetCustomersRequest request) {
                return customerRepository.findByIds(request.getCustomerIds());
        }

        public BatchOperationResponse deleteCustomersBatch(BatchDeleteCustomersRequest request) {
                long deletedCount = customerRepository.deleteByIds(request.getCustomerIds());
                return BatchOperationResponse.builder()
                                .success(true)
                                .affectedCount(deletedCount)
                                .message("Successfully deleted " + deletedCount + " customers")
                                .build();
        }

        public BatchOperationResponse updateCustomerStatusBatch(BatchUpdateCustomerStatusRequest request) {
                long updatedCount = customerRepository.updateStatusByIds(request.getCustomerIds(), request.getStatus());
                return BatchOperationResponse.builder()
                                .success(true)
                                .affectedCount(updatedCount)
                                .message("Successfully updated status for " + updatedCount + " customers")
                                .build();
        }

        public BatchOperationResponse archiveCustomersBatch(BatchArchiveCustomersRequest request) {
                long archivedCount = customerRepository.archiveByIds(request.getCustomerIds());
                return BatchOperationResponse.builder()
                                .success(true)
                                .affectedCount(archivedCount)
                                .message("Successfully archived " + archivedCount + " customers")
                                .build();
        }

        public BatchOperationResponse restoreCustomersBatch(BatchRestoreCustomersRequest request) {
                long restoredCount = customerRepository.restoreByIds(request.getCustomerIds());
                return BatchOperationResponse.builder()
                                .success(true)
                                .affectedCount(restoredCount)
                                .message("Successfully restored " + restoredCount + " customers")
                                .build();
        }

        public List<CustomerUser> getCustomerUsers(String customerId) {
                return customerRepository.findUsersByCustomerId(customerId);
        }

        public CustomerUser updateCustomerUserRole(
                        String customerId,
                        String userId,
                        UpdateUserRoleRequest request) {
                String normalizedRole = request.getRole().trim().toUpperCase();

                return customerRepository
                                .updateUserRole(
                                                customerId,
                                                userId,
                                                normalizedRole)
                                .orElseThrow(() -> new CustomerUserNotFoundException(
                                                customerId,
                                                userId));
        }

        public Customer getCustomerById(String customerId) {
                return customerRepository.findById(customerId)
                                .orElseThrow(
                                                () -> new CustomerNotFoundException(customerId));
        }

        public long countCustomer(String keyword, CustomerStatus status, String category) {
                return customerRepository.count(keyword, status, category);
        }

        public CustomerPageResponse filterCustomers(CustomerStatus status, String category,
                        LocalDate createdFrom, LocalDate createdTo, int page, int size) {
                List<Customer> customers = customerRepository.filter(status, category, createdFrom, createdTo);

                return CustomerPageResponse.builder()
                                .content(customers)
                                .page(page)
                                .size(size)
                                .totalElements(customers.size())
                                .totalPages(1)
                                .first(true)
                                .last(true)
                                .build();
        }

        public CustomerPageResponse searchCustomers(String keyword, int page, int size) {
                List<Customer> customers = customerRepository.search(keyword, page, size);

                return CustomerPageResponse.builder()
                                .content(customers)
                                .page(page)
                                .size(size)
                                .totalElements(customers.size())
                                .totalPages(1)
                                .first(true)
                                .last(true)
                                .build();
        }

        public CustomerPageResponse getCustomers(int page, int size) {
                if (page < 0) {
                        throw new IllegalArgumentException(
                                        "Page must be greater than or equal to 0");
                }

                if (size < 1 || size > 100) {
                        throw new IllegalArgumentException(
                                        "Size must be between 1 and 100");
                }

                List<Customer> customers = customerRepository.findAll()
                                .stream()
                                .sorted(Comparator.comparing(Customer::getCompanyName))
                                .toList();

                int totalElements = customers.size();

                int totalPages = totalElements == 0
                                ? 0
                                : (int) Math.ceil((double) totalElements / size);

                int fromIndex = Math.min(page * size, totalElements);
                int toIndex = Math.min(fromIndex + size, totalElements);

                List<Customer> content = customers.subList(fromIndex, toIndex);

                return CustomerPageResponse.builder()
                                .content(content)
                                .page(page)
                                .size(size)
                                .totalElements(totalElements)
                                .totalPages(totalPages)
                                .first(page == 0)
                                .last(totalPages == 0 || page >= totalPages - 1)
                                .build();
        }

        public Customer createCustomer(CreateCustomerRequest request) {
                String normalizedCompanyName = request.getCompanyName().trim();
                String normalizedDomain = request.getDomain().trim().toLowerCase();
                if (customerRepository.existsByDomain(normalizedDomain)) {
                        throw new CustomerDomainAlreadyExistsException(normalizedDomain);
                }

                OffsetDateTime now = OffsetDateTime.now();

                Customer customer = Customer.builder()
                                .id(generateCustomerId())
                                .companyName(normalizedCompanyName)
                                .domain(normalizedDomain)
                                .logoUrl(trimToNull(request.getLogoUrl()))
                                .status(request.getStatus())
                                .category(trimToNull(request.getCategory()))
                                .description(trimToNull(request.getDescription()))
                                .users(new ArrayList<>())
                                .totalUsers(0)
                                .createdAt(now)
                                .updatedAt(now)
                                .deletedAt(null)
                                .build();

                return customerRepository.save(customer);
        }

        private String generateCustomerId() {
                return "cus_" + UUID.randomUUID();
        }

        private String trimToNull(String value) {
                if (value == null) {
                        return null;
                }

                String trimmedValue = value.trim();

                return trimmedValue.isEmpty()
                                ? null
                                : trimmedValue;
        }
}
