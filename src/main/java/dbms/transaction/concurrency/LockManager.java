package dbms.transaction.concurrency;

public class LockManager implements ILockManager {

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

    @Override
    public void lock(LockRequest request) {

    }

    @Override
    public void unlock(LockRequest request) {

    }

}
