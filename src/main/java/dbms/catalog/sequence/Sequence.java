package dbms.catalog.sequence;

import dbms.catalog.base.DatabaseObject;
import dbms.catalog.base.DatabaseObjectVisitor;
import dbms.catalog.base.DropMode;
import dbms.catalog.base.LifecycleStatus;

public class Sequence extends DatabaseObject {
    public long start;
    public long increment;

    public Sequence() {
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
    }

    public Sequence(String seqId, String name, long start, long increment) {
        this.objectId = seqId;
        this.name = name;
        this.start = start;
        this.increment = increment;
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
    }

    public long nextValue() {
        return 0;
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

