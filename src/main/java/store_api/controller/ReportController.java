package store_api.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import store_api.model.report.StoreReport;
import store_api.service.ReportService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<List<StoreReport>> getReports() {
        return ResponseEntity.ok(reportService.getReports());
    }
}
