package store_api.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import store_api.model.report.StoreReport;
import store_api.repository.ReportRepository;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    public List<StoreReport> getReports() {
        return reportRepository.findAll();
    }
}
