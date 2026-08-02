package store_api.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import store_api.model.customer.Customer;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPageResponse {
    private List<Customer> content;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    private boolean first;

    private boolean last;
}
