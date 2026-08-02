package store_api.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchRestoreCustomersRequest {
    @NotEmpty(message = "Customer IDs list cannot be empty")
    private List<String> customerIds;
}
