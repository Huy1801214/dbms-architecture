package dbms.transaction.manager;

import dbms.transaction.concurrency.ConcurrencyManager;

public class TransactionManager {

    private Transaction transaction;
    private TransactionLog transactionLog;
    private ConcurrencyManager concurrencyManager;

    public TransactionManager() {
    }

    public TransactionManager(Transaction transaction,
            TransactionLog transactionLog,
            ConcurrencyManager concurrencyManager) {
        this.transaction = transaction;
        this.transactionLog = transactionLog;
        this.concurrencyManager = concurrencyManager;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
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
