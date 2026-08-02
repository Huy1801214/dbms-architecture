package store_api.repository;

import java.util.List;

import store_api.model.report.StoreReport;

public interface ReportRepository {
    List<StoreReport> findAll();
}
