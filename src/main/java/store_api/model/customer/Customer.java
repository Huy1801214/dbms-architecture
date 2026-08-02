package store_api.model.customer;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String id;

    private String companyName;

    private String domain;

    private String logoUrl;

    private CustomerStatus status;

    private String category;

    private String description;

    @Builder.Default
    private List<CustomerUserSummary> users = new ArrayList<>();

    @Builder.Default
    private Integer totalUsers = 0;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private OffsetDateTime deletedAt;
}
