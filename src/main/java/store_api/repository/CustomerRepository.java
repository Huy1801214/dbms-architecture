package store_api.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import store_api.model.customer.Customer;
import store_api.model.customer.CustomerStatus;
import store_api.model.customer.CustomerUser;

public interface CustomerRepository {
    List<Customer> findAll();

    boolean existsByDomain(String domain);

    Customer save(Customer customer);

    List<Customer> search(String keyword, int page, int size);

    List<Customer> filter(CustomerStatus status, String category, LocalDate createdFrom, LocalDate createdTo);

    long count(String keyword, CustomerStatus status, String category);

    Optional<Customer> findById(String id);

    List<Customer> findByIds(List<String> customerIds);

    Optional<CustomerUser> updateUserRole(String customerId, String userId, String role);
}
