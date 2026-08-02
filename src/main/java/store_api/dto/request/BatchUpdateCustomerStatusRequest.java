package store_api.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import store_api.model.customer.CustomerStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchUpdateCustomerStatusRequest {
    @NotEmpty(message = "Customer IDs list cannot be empty")
    private List<String> customerIds;

    @NotNull(message = "Status is required")
    private CustomerStatus status;
}
