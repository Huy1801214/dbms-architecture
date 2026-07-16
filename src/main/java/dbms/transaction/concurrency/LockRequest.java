package dbms.transaction.concurrency;

public class LockRequest {

    private int transactionId;
    private String resourceId;
    private Lock lock;

    public LockRequest() {
    }

    public LockRequest(int transactionId, String resourceId, Lock lock) {
        this.transactionId = transactionId;
        this.resourceId = resourceId;
        this.lock = lock;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

}
