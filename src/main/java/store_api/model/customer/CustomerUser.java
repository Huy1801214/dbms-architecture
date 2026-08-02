package store_api.model.customer;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUser {
    private String id;

    private String fullName;

    private String email;

    private String avatarUrl;

    private String role;

    private Boolean active;

    private OffsetDateTime createdAt;
}
