package dbms.transaction.concurrency;

public class ConcurrencyManager {

    private ILockManager lockManager;

    public ConcurrencyManager() {
    }

    public ConcurrencyManager(ILockManager lockManager) {
        this.lockManager = lockManager;
    }

    public ILockManager getLockManager() {
        return lockManager;
    }

    public void setLockManager(ILockManager lockManager) {
        this.lockManager = lockManager;
    }

    public void acquire(LockRequest request) {

    }

    public void release(LockRequest request) {

    }

}
