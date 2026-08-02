package store_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import store_api.model.customer.CustomerStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest {
    @NotBlank(message = "Company name is required")
    @Size(max = 150, message = "Company name cannot exceed 255 characters")
    private String companyName;

    @NotBlank(message = "Domain is required")
    @Size(max = 255, message = "Domain cannot exceed 255 characters")
    private String domain;
    private String logoUrl;
    @NotNull(message = "Customer status is required")
    private CustomerStatus status;
    @Size(max = 100, message = "Category must not exceed 100 characters")
    private String category;
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
}
