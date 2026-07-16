package dbms.transaction.concurrency;

public class ConcurrencyManager {

    private LockManager lockManager;

    public ConcurrencyManager() {
    }

    public ConcurrencyManager(LockManager lockManager) {
        this.lockManager = lockManager;
    }

    public LockManager getLockManager() {
        return lockManager;
    }

    public void setLockManager(LockManager lockManager) {
        this.lockManager = lockManager;
    }

    public void acquire(LockRequest lockRequest) {

    }

    public void release(LockRequest lockRequest) {

    }

}
