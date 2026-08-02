package store_api.model.report;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreReport {
    private String id;
    private String title;
    private String type;
    private String status;
    private String downloadUrl;
    private OffsetDateTime generatedAt;
}
