package dbms.transaction.concurrency;

import java.util.ArrayList;
import java.util.List;

public class LockTable {

    private List<Lock> locks;

    public LockTable() {
        this.locks = new ArrayList<>();
    }

    public LockTable(List<Lock> locks) {
        this.locks = locks;
    }

    public List<Lock> getLocks() {
        return locks;
    }

    public void setLocks(List<Lock> locks) {
        this.locks = locks;
    }

}
