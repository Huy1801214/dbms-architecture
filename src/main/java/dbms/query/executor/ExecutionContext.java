package dbms.query.executor;

import dbms.storage.buffer.BufferManager;
import dbms.transaction.manager.TransactionManager;

public class ExecutionContext {

    private BufferManager bufferManager;
    private TransactionManager transactionManager;

    public ExecutionContext() {
    }

    public ExecutionContext(BufferManager bufferManager,
            TransactionManager transactionManager) {
        this.bufferManager = bufferManager;
        this.transactionManager = transactionManager;
    }

    public BufferManager getBufferManager() {
        return bufferManager;
    }

    public void setBufferManager(BufferManager bufferManager) {
        this.bufferManager = bufferManager;
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
