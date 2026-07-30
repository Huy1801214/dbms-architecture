package dbms;

import dbms.recovery.service.WALManager;
import dbms.transaction.service.MVCCManager;
import dbms.transaction.service.TransactionManager;

public class DBMSApplication {
    public static void main(String[] args) {
        WALManager walManager = new WALManager();
        MVCCManager mvccManager = new MVCCManager();
        TransactionManager transactionManager = new TransactionManager(walManager, mvccManager);
        transactionManager.beginTransaction();
    }
}
