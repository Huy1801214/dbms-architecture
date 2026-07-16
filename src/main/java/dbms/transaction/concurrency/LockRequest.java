package dbms.transaction.concurrency;

import dbms.transaction.manager.Transaction;

public class LockRequest {

    private Transaction transaction;
    private Lock lock;

    public LockRequest() {
    }

    public LockRequest(Transaction transaction, Lock lock) {
        this.transaction = transaction;
        this.lock = lock;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

}
