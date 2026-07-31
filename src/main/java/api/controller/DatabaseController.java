package api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import api.dto.DatabaseCreateRequest;
import api.dto.DatabaseResponse;
import api.service.DatabaseService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping({ "/api/v1/databases" })
public class DatabaseController {
    private final DatabaseService service;

    public DatabaseController(DatabaseService service) {
        this.service = service;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public DatabaseResponse createDatabase(@RequestBody DatabaseCreateRequest request) {
        return service.createDatabase(request);
    }
}
