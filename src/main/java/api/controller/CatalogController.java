package api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.dto.DatabaseResponse;
import api.service.DatabaseService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping({ "/api/v1/catalog", "/catalog" })
public class CatalogController {
    private final DatabaseService service;

    public CatalogController(DatabaseService service) {
        this.service = service;
    }

    @GetMapping("/databases")
    public List<DatabaseResponse> getAllDatabases() {
        return service.getAllDatabases();
    }
}
