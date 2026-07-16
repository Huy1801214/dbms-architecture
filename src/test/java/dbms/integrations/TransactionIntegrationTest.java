package dbms.integrations;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import dbms.transaction.manager.*;
import dbms.transaction.concurrency.*;

public class TransactionIntegrationTest {

    private TransactionLog transactionLog;
    private ConcurrencyManager concurrencyManager;
    private TransactionManager transactionManager;

    void setup() {
        transactionLog = new TransactionLog();
        concurrencyManager = new ConcurrencyManager(new LockManager(new LockTable()));
        transactionManager = new TransactionManager(transactionLog, concurrencyManager);
    }

    @Test
    void shouldCommitTransactionSuccessfully() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void shouldRollbackTransactionWhenExecutionFails() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void shouldWriteTransactionLogAfterCommit() {
        // Arrange

        // Act

        // Assert
    }
}
