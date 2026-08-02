package store_api.model.email;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailCampaign {
    private String id;
    private String name;
    private String subject;
    private String status;
    private Integer recipientCount;
    private OffsetDateTime sentAt;
    private OffsetDateTime createdAt;
}
