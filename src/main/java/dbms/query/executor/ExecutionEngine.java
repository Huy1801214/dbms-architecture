package dbms.query.executor;

import dbms.query.parser.ASTNode;
import dbms.storage.buffer.BufferManager;
import dbms.transaction.manager.TransactionManager;

public class ExecutionEngine {

    private ExecutionContext executionContext;
    private Executor executor;
    private BufferManager bufferManager;
    private TransactionManager transactionManager;

    public ExecutionEngine() {
    }

    public ExecutionEngine(ExecutionContext executionContext,
            Executor executor,
            BufferManager bufferManager,
            TransactionManager transactionManager) {
        this.executionContext = executionContext;
        this.executor = executor;
        this.bufferManager = bufferManager;
        this.transactionManager = transactionManager;
    }

    public ExecutionContext getExecutionContext() {
        return executionContext;
    }

    public void setExecutionContext(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
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

    public ResultSet execute(ASTNode astNode) {
        return null;
    }

}
