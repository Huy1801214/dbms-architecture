package dbms.transaction.manager;

public class TransactionContext {

    private long startTime;

    public TransactionContext() {
    }

    public TransactionContext(long startTime) {
        this.startTime = startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

}
