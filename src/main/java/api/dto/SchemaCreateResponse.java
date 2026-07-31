package api.dto;

import java.util.UUID;

public class SchemaCreateResponse {
    private UUID id;
    private String name, owner, databaseId, lifecycleStatus;

    public SchemaCreateResponse(UUID id, String name, String owner, String databaseId, String lifecycleStatus) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.databaseId = databaseId;
        this.lifecycleStatus = lifecycleStatus;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }

    public String getLifecycleStatus() {
        return lifecycleStatus;
    }

    public void setLifecycleStatus(String lifecycleStatus) {
        this.lifecycleStatus = lifecycleStatus;
    }

}
