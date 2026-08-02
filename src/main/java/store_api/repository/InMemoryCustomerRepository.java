package store_api.repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import store_api.model.customer.Customer;
import store_api.model.customer.CustomerStatus;
import store_api.model.customer.CustomerUserSummary;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {
        private final Map<String, Customer> customerStorage = new ConcurrentHashMap<>();

        @PostConstruct
        public void initializeMockData() {
                OffsetDateTime now = OffsetDateTime.now();

                Customer framer = Customer.builder()
                                .id("cus_001")
                                .companyName("Framer")
                                .domain("framer.com")
                                .logoUrl("https://example.com/logos/framer.png")
                                .status(CustomerStatus.CUSTOMER)
                                .category("Design Tools")
                                .description("Make beautiful websites in minutes.")
                                .users(List.of(
                                                CustomerUserSummary.builder()
                                                                .id("usr_001")
                                                                .fullName("Alex Morgan")
                                                                .avatarUrl("https://example.com/avatars/usr_001.png")
                                                                .build()))
                                .totalUsers(8)
                                .createdAt(now)
                                .updatedAt(now)
                                .build();

                Customer figma = Customer.builder()
                                .id("cus_002")
                                .companyName("Figma")
                                .domain("figma.com")
                                .logoUrl("https://example.com/logos/figma.png")
                                .status(CustomerStatus.CUSTOMER)
                                .category("Design Tools")
                                .description("Collaborative interface design tool.")
                                .users(List.of(
                                                CustomerUserSummary.builder()
                                                                .id("usr_002")
                                                                .fullName("Emma Brown")
                                                                .avatarUrl("https://example.com/avatars/usr_002.png")
                                                                .build()))
                                .totalUsers(10)
                                .createdAt(now)
                                .updatedAt(now)
                                .build();

                Customer stripe = Customer.builder()
                                .id("cus_003")
                                .companyName("Stripe")
                                .domain("stripe.com")
                                .logoUrl("https://example.com/logos/stripe.png")
                                .status(CustomerStatus.ACTIVE)
                                .category("Financial Tools")
                                .description("Payment infrastructure for the internet.")
                                .users(List.of())
                                .totalUsers(6)
                                .createdAt(now)
                                .updatedAt(now)
                                .build();

                customerStorage.put(framer.getId(), framer);
                customerStorage.put(figma.getId(), figma);
                customerStorage.put(stripe.getId(), stripe);
        }

        @Override
        public List<Customer> findAll() {
                return new ArrayList<>(customerStorage.values());
        }

        @Override
        public boolean existsByDomain(String domain) {
                if (domain == null) {
                        return false;
                }

                return customerStorage.values()
                                .stream()
                                .map(Customer::getDomain)
                                .filter(existingDomain -> existingDomain != null)
                                .anyMatch(existingDomain -> existingDomain.equalsIgnoreCase(domain));
        }

        @Override
        public Customer save(Customer customer) {
                customerStorage.put(customer.getId(), customer);
                return customer;
        }

        @Override
        public List<Customer> search(String keyword, int page, int size) {
                OffsetDateTime now = OffsetDateTime.now();

                Customer figma = Customer.builder()
                                .id("cus_001")
                                .companyName("Figma")
                                .domain("figma.com")
                                .logoUrl("https://example.com/logos/figma.png")
                                .status(CustomerStatus.CUSTOMER)
                                .category("Design Tools")
                                .description("The collaborative interface design tool.")
                                .users(List.of())
                                .totalUsers(10)
                                .createdAt(now)
                                .updatedAt(now)
                                .deletedAt(null)
                                .build();

                Customer framer = Customer.builder()
                                .id("cus_002")
                                .companyName("Framer")
                                .domain("framer.com")
                                .logoUrl("https://example.com/logos/framer.png")
                                .status(CustomerStatus.CUSTOMER)
                                .category("Design Tools")
                                .description("Make beautiful websites in minutes.")
                                .users(List.of())
                                .totalUsers(8)
                                .createdAt(now)
                                .updatedAt(now)
                                .deletedAt(null)
                                .build();

                return List.of(figma, framer);
        }

}
