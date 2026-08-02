package store_api.repository;

import java.time.LocalDate;
import java.util.List;
import store_api.model.customer.Customer;
import store_api.model.customer.CustomerStatus;

public interface CustomerRepository {
    List<Customer> findAll();

    boolean existsByDomain(String domain);

    Customer save(Customer customer);

    List<Customer> search(String keyword, int page, int size);

    List<Customer> filter(CustomerStatus status, String category, LocalDate createdFrom, LocalDate createdTo);

    long count(String keyword, CustomerStatus status, String category);
}
