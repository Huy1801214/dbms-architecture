package dbms.catalog;

public class View extends DatabaseObject {
    public String queryDefinition;

    public View() {}

    public View(String viewId, String name, String queryDefinition) {
        this.objectId = viewId;
        this.name = name;
        this.queryDefinition = queryDefinition;
    }

    @Override
    public void create() {}

    @Override
    public void drop() {}

    @Override
    public void rename(String newName) {}

    @Override
    public void accept(DatabaseObjectVisitor visitor) {}
}