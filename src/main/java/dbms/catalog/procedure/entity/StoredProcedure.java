package dbms.catalog.procedure.entity;

import dbms.catalog.base.entity.DatabaseObject;
import dbms.catalog.base.enums.DropMode;
import dbms.catalog.base.enums.LifecycleStatus;
import dbms.catalog.base.visitor.DatabaseObjectVisitor;

public class StoredProcedure extends DatabaseObject {
    public String code;

    public StoredProcedure() {
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
    }

    public StoredProcedure(String procId, String name, String code) {
        this.objectId = procId;
        this.name = name;
        this.code = code;
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
    }

    public void execute() {}

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

