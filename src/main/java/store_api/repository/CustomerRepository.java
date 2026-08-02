package store_api.repository;

import java.util.List;
import store_api.model.customer.Customer;

public interface CustomerRepository {
    List<Customer> findAll();
}
