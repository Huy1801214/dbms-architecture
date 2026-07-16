package dbms.transaction.manager;

import dbms.transaction.concurrency.ConcurrencyManager;

public class TransactionManager {

    private TransactionLog transactionLog;
    private ConcurrencyManager concurrencyManager;

    public TransactionManager() {
    }

    public TransactionManager(TransactionLog transactionLog,
            ConcurrencyManager concurrencyManager) {
        this.transactionLog = transactionLog;
        this.concurrencyManager = concurrencyManager;
    }

    public TransactionLog getTransactionLog() {
        return transactionLog;
    }

    public void setTransactionLog(TransactionLog transactionLog) {
        this.transactionLog = transactionLog;
    }

    public ConcurrencyManager getConcurrencyManager() {
        return concurrencyManager;
    }

    public void setConcurrencyManager(ConcurrencyManager concurrencyManager) {
        this.concurrencyManager = concurrencyManager;
    }

    public Transaction begin() {
        return null;
    }

    public void commit(Transaction transaction) {

    }

    public void rollback(Transaction transaction) {

    }

}
