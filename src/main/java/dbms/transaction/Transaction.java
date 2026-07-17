package dbms.transaction;
import java.time.LocalDateTime;

public class Transaction {
    public String transactionId;
    public String isolationLevel;
    public String state;
    public LocalDateTime startTime;

    public void begin() {}
    public void commit() {}
    public void rollback() {}
    public void savepoint(String name) {}
}