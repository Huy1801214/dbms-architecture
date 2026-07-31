package api.service;

import java.util.List;
import org.springframework.stereotype.Service;
import api.dto.DatabaseResponse;
import api.repository.DatabaseRepository;

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
}
