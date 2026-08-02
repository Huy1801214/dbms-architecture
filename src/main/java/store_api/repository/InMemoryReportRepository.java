package store_api.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import store_api.model.report.StoreReport;

@Repository
public class InMemoryReportRepository implements ReportRepository {

    @Override
    public List<StoreReport> findAll() {
        OffsetDateTime now = OffsetDateTime.now();

        StoreReport r1 = StoreReport.builder()
                .id("rpt_001")
                .title("Q2 Sales Summary")
                .type("SALES")
                .status("READY")
                .downloadUrl("https://example.com/reports/q2-sales.pdf")
                .generatedAt(now.minusDays(1))
                .build();

        StoreReport r2 = StoreReport.builder()
                .id("rpt_002")
                .title("Monthly Customer Traffic Analysis")
                .type("TRAFFIC")
                .status("READY")
                .downloadUrl("https://example.com/reports/july-traffic.pdf")
                .generatedAt(now.minusDays(3))
                .build();

        return List.of(r1, r2);
    }
}
