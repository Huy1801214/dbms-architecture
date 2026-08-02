package store_api.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import store_api.model.email.EmailCampaign;

@Repository
public class InMemoryEmailCampaignRepository implements EmailCampaignRepository {

    @Override
    public List<EmailCampaign> findAll() {
        OffsetDateTime now = OffsetDateTime.now();

        EmailCampaign c1 = EmailCampaign.builder()
                .id("cmp_001")
                .name("Summer Sale Announcement")
                .subject("Get 20% off on all items this weekend!")
                .status("SENT")
                .recipientCount(1250)
                .sentAt(now.minusDays(2))
                .createdAt(now.minusDays(5))
                .build();

        EmailCampaign c2 = EmailCampaign.builder()
                .id("cmp_002")
                .name("Monthly Newsletter - August")
                .subject("Check out our latest product updates")
                .status("SCHEDULED")
                .recipientCount(3400)
                .sentAt(null)
                .createdAt(now.minusHours(12))
                .build();

        return List.of(c1, c2);
    }
}
