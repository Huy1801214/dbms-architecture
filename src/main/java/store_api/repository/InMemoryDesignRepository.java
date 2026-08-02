package store_api.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import store_api.model.design.StoreDesign;

@Repository
public class InMemoryDesignRepository implements DesignRepository {

    @Override
    public List<StoreDesign> findAll() {
        OffsetDateTime now = OffsetDateTime.now();

        StoreDesign d1 = StoreDesign.builder()
                .id("dsg_001")
                .themeName("Modern Dark Grid")
                .version("v2.4.0")
                .active(true)
                .previewUrl("https://example.com/themes/dark-grid-preview.png")
                .lastAppliedAt(now.minusDays(10))
                .build();

        StoreDesign d2 = StoreDesign.builder()
                .id("dsg_002")
                .themeName("Minimalist Light Commerce")
                .version("v1.8.2")
                .active(false)
                .previewUrl("https://example.com/themes/light-commerce-preview.png")
                .lastAppliedAt(now.minusMonths(2))
                .build();

        return List.of(d1, d2);
    }
}
