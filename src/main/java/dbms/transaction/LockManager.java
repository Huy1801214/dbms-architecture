package dbms.transaction;

public class LockManager {
    public void acquireLock(String resourceId, String transactionId, String lockMode) {}
    public void releaseLock(String resourceId, String transactionId) {}
    public void detectDeadlock() {}
}