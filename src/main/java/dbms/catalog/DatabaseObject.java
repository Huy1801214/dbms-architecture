package dbms.catalog;

public abstract class DatabaseObject {
    protected String objectId;
    protected String name;
    protected String owner;

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
