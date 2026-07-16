package dbms.transaction.concurrency;

public class LockManager {

    private LockTable lockTable;

    public LockManager() {
    }

    public LockManager(LockTable lockTable) {
        this.lockTable = lockTable;
    }

    public LockTable getLockTable() {
        return lockTable;
    }

    public void setLockTable(LockTable lockTable) {
        this.lockTable = lockTable;
    }

    public void lock(LockRequest lockRequest) {

    }

    public void unlock(LockRequest lockRequest) {

    }

}
