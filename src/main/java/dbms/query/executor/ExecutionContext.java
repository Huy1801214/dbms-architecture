package dbms.query.executor;

import dbms.storage.buffer.BufferManager;
import dbms.transaction.manager.Transaction;

public class ExecutionContext {

    private Transaction transaction;
    private BufferManager bufferManager;

    public ExecutionContext() {
    }

    public ExecutionContext(Transaction transaction,
            BufferManager bufferManager) {
        this.transaction = transaction;
        this.bufferManager = bufferManager;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public BufferManager getBufferManager() {
        return bufferManager;
    }

    public void setBufferManager(BufferManager bufferManager) {
        this.bufferManager = bufferManager;
    }

}
