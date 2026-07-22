package dbms.catalog.base;

import java.util.UUID;

public abstract class DatabaseObject implements DatabaseComponent {
    public String objectId;
    public String name;
    public String owner;
    public String schemaId;

    public abstract void create();
    public abstract void drop();
    public abstract void rename(String newName);
    public abstract void accept(DatabaseObjectVisitor visitor);

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
        return null;
    }
}
