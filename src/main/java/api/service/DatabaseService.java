package api.service;

import java.util.List;
import org.springframework.stereotype.Service;

import api.dto.DatabaseCreateRequest;
import api.dto.DatabaseResponse;
import api.repository.DatabaseRepository;
import dbms.catalog.database.entity.Database;
import dbms.catalog.database.enums.DatabaseStatus;

@Service
public class DatabaseService {
    private final DatabaseRepository repository;

    public DatabaseService(DatabaseRepository repository) {
        this.repository = repository;
    }

    public List<DatabaseResponse> getAllDatabases() {
        return repository.findAllDatabases()
                .stream()
                .map(database -> new DatabaseResponse(
                        database.getId(),
                        database.getName(),
                        database.getOwner(),
                        database.getStatus() != null
                                ? database.getStatus().name()
                                : "OFFLINE",
                        database.getLifecycleStatus() != null
                                ? database.getLifecycleStatus().name()
                                : "ACTIVE"))
                .toList();
    }

    public DatabaseResponse createDatabase(DatabaseCreateRequest request) {
        DatabaseStatus status = DatabaseStatus.OPENING;
        Database database = new Database(null, request.getName(), request.getOwner(), status,
                java.time.LocalDateTime.now());
        Database saved = repository.save(database);
        return new DatabaseResponse(
                saved.getId(),
                saved.getName(),
                saved.getOwner(),
                saved.getStatus().name(),
                saved.getLifecycleStatus() != null ? saved.getLifecycleStatus().name() : "ACTIVE");
    }
}
