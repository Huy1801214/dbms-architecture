package store_api.repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import store_api.model.customer.Customer;
import store_api.model.customer.CustomerStatus;
import store_api.model.customer.CustomerUser;
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

        @Override
        public List<Customer> filter(CustomerStatus status, String category, LocalDate createdFrom,
                        LocalDate createdTo) {
                Customer figma = Customer.builder()
                                .id("cus_001")
                                .companyName("Figma")
                                .domain("figma.com")
                                .status(CustomerStatus.ACTIVE)
                                .category("Design Tools")
                                .description("Collaborative interface design platform.")
                                .users(List.of())
                                .totalUsers(10)
                                .createdAt(OffsetDateTime.now())
                                .updatedAt(OffsetDateTime.now())
                                .deletedAt(null)
                                .build();

                Customer stripe = Customer.builder()
                                .id("cus_002")
                                .companyName("Stripe")
                                .domain("stripe.com")
                                .status(CustomerStatus.ACTIVE)
                                .category("Financial Tools")
                                .description("Payment infrastructure for online businesses.")
                                .users(List.of())
                                .totalUsers(8)
                                .createdAt(OffsetDateTime.now())
                                .updatedAt(OffsetDateTime.now())
                                .deletedAt(null)
                                .build();

                return List.of(figma, stripe);
        }

        @Override
        public long count(String keyword, CustomerStatus status, String category) {
                return 125L;
        }

        @Override
        public Optional<Customer> findById(String customerId) {
                if (!"cus_001".equals(customerId)) {
                        return Optional.empty();
                }

                Customer customer = Customer.builder()
                                .id("cus_001")
                                .companyName("Figma")
                                .domain("figma.com")
                                .logoUrl("https://example.com/logos/figma.png")
                                .status(CustomerStatus.ACTIVE)
                                .category("Design Tools")
                                .description("The collaborative interface design tool.")
                                .users(List.of())
                                .totalUsers(10)
                                .createdAt(OffsetDateTime.now())
                                .updatedAt(OffsetDateTime.now())
                                .deletedAt(null)
                                .build();

                return Optional.of(customer);
        }

        @Override
        public Optional<CustomerUser> updateUserRole(String customerId, String userId, String role) {
                if (!"cus_001".equals(customerId)
                                || !"usr_001".equals(userId)) {
                        return Optional.empty();
                }

                CustomerUser user = CustomerUser.builder()
                                .id(userId)
                                .fullName("Alex Morgan")
                                .email("alex@example.com")
                                .avatarUrl("https://example.com/avatars/usr_001.png")
                                .role(role)
                                .active(true)
                                .createdAt(OffsetDateTime.now())
                                .build();

                return Optional.of(user);
        }

        @Override
        public List<Customer> findByIds(List<String> customerIds) {
                if (customerIds == null || customerIds.isEmpty()) {
                        return List.of();
                }

                List<Customer> found = customerIds.stream()
                                .map(customerStorage::get)
                                .filter(Objects::nonNull)
                                .toList();

                if (!found.isEmpty()) {
                        return found;
                }

                // Fallback mock items if storage is empty
                Customer figma = Customer.builder()
                                .id("cus_001")
                                .companyName("Figma")
                                .domain("figma.com")
                                .status(CustomerStatus.ACTIVE)
                                .category("Design Tools")
                                .description("Collaborative interface design platform.")
                                .users(List.of())
                                .totalUsers(10)
                                .createdAt(OffsetDateTime.now())
                                .updatedAt(OffsetDateTime.now())
                                .build();

                Customer stripe = Customer.builder()
                                .id("cus_002")
                                .companyName("Stripe")
                                .domain("stripe.com")
                                .status(CustomerStatus.ACTIVE)
                                .category("Financial Tools")
                                .description("Payment infrastructure for online businesses.")
                                .users(List.of())
                                .totalUsers(8)
                                .createdAt(OffsetDateTime.now())
                                .updatedAt(OffsetDateTime.now())
                                .build();

                return List.of(figma, stripe);
        }

        @Override
        public long deleteByIds(List<String> customerIds) {
                if (customerIds == null || customerIds.isEmpty()) {
                        return 0L;
                }

                long count = 0;
                for (String id : customerIds) {
                        if (customerStorage.remove(id) != null) {
                                count++;
                        }
                }

                if (count == 0 && !customerIds.isEmpty()) {
                        return customerIds.size();
                }

                return count;
        }

}
