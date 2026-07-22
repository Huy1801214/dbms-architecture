package dbms.catalog.base;

public abstract class DatabaseObject {
    public String objectId;
    public String name;
    public String owner;

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
}
