package dbms.catalog;

public class StoredProcedure extends DatabaseObject {
    public String code;

    public StoredProcedure() {}

    public StoredProcedure(String procId, String name, String code) {
        this.objectId = procId;
        this.name = name;
        this.code = code;
    }

    public void execute() {}

    @Override
    public void create() {}

    @Override
    public void drop() {}

    @Override
    public void rename(String newName) {}

    @Override
    public void accept(DatabaseObjectVisitor visitor) {}
}