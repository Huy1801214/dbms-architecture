package dbms.transaction.concurrency;

public interface ILockManager {

    void lock(LockRequest request);

    void unlock(LockRequest request);

}
