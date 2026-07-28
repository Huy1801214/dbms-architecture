package dbms.transaction.service;

import dbms.transaction.entity.Transaction;

public class TransactionManager {
    public Transaction beginTransaction() { return null; }
    public void commit(String transactionId) {}
    public void rollback(String transactionId) {}
    public void recover() {}
}
