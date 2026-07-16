package dbms.transaction.concurrency;

import java.util.HashMap;
import java.util.Map;

public class LockTable {

    private Map<String, Lock> locks;

    public LockTable() {
        this.locks = new HashMap<>();
    }

    public LockTable(Map<String, Lock> locks) {
        this.locks = locks;
    }

    public Map<String, Lock> getLocks() {
        return locks;
    }

    public void setLocks(Map<String, Lock> locks) {
        this.locks = locks;
    }

}
