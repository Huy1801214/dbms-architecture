package api.dto;

import java.util.UUID;

public class DatabaseResponse {
    private final UUID id;
    private final String name;
    private final String owner;
    private final String status;
    private final String lifecycleStatus;

    public DatabaseResponse(UUID id, String name, String owner, String status, String lifecycleStatus) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.status = status;
        this.lifecycleStatus = lifecycleStatus;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getOwner() {
        return owner;
    }

    public String getLifecycleStatus() {
        return lifecycleStatus;
    }
}
