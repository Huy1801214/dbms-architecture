package dbms.catalog.base;

import java.util.UUID;
import java.util.List;
import java.util.Collections;

public abstract class DatabaseObject implements DatabaseComponent {
    public String objectId;
    public String name;
    public String owner;
    public String schemaId;
    protected LifecycleStatus lifecycleStatus = LifecycleStatus.ACTIVE;

    public abstract void create();

    public abstract void accept(DatabaseObjectVisitor visitor);

    public abstract void rename(String newName);

    public abstract void drop(DropMode mode);

    public String getObjectId() {
        return objectId;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public String getQualifiedName() {
        return name;
    }

    @Override
    public LifecycleStatus getLifecycleStatus() {
        return lifecycleStatus;
    }

    @Override
    public List<DatabaseComponent> getChildren() {
        return Collections.emptyList();
    }
}
