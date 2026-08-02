package dbms.catalog.view.entity;

import dbms.catalog.base.entity.DatabaseObject;
import dbms.catalog.base.enums.DropMode;
import dbms.catalog.base.enums.LifecycleStatus;
import dbms.catalog.base.visitor.DatabaseObjectVisitor;

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

