package dbms.catalog.view;

import dbms.catalog.base.DatabaseObject;
import dbms.catalog.base.DatabaseObjectVisitor;
import dbms.catalog.base.DropMode;
import dbms.catalog.base.LifecycleStatus;

public class View extends DatabaseObject {
    public String queryDefinition;

    public View() {
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
    }

    public View(String viewId, String name, String queryDefinition) {
        this.objectId = viewId;
        this.name = name;
        this.queryDefinition = queryDefinition;
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
    }

    @Override
    public void create() {}

    @Override
    public void drop(DropMode mode) {
        this.lifecycleStatus = LifecycleStatus.DROPPED;
    }

    @Override
    public void rename(String newName) {
        this.name = newName;
    }

    @Override
    public void accept(DatabaseObjectVisitor visitor) {
        if (visitor != null) {
            visitor.visit(this);
        }
    }
}

