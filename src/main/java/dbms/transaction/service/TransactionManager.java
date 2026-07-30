package dbms.transaction.service;

import dbms.recovery.service.WALManager;
import dbms.transaction.entity.Transaction;

public class TransactionManager {
    private final WALManager walManager;
    private final MVCCManager mvccManager;

    public TransactionManager(WALManager walManager, MVCCManager mvccManager) {
        this.walManager = walManager;
        this.mvccManager = mvccManager;
    }

    public Transaction beginTransaction() {
        return null;
    }

    public void commit(String transactionId) {
    }

    public void rollback(String transactionId) {
    }

    public void recover() {
    }
}
