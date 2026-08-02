package store_api.model.design;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreDesign {
    private String id;
    private String themeName;
    private String version;
    private Boolean active;
    private String previewUrl;
    private OffsetDateTime lastAppliedAt;
}
