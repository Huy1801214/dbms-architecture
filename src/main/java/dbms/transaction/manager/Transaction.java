package dbms.transaction.manager;

public class Transaction {

    private int transactionId;
    private TransactionState state;
    private TransactionContext transactionContext;

    public Transaction() {
    }

    public Transaction(int transactionId,
            TransactionState state,
            TransactionContext transactionContext) {
        this.transactionId = transactionId;
        this.state = state;
        this.transactionContext = transactionContext;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }

    public TransactionContext getTransactionContext() {
        return transactionContext;
    }

    public void setTransactionContext(TransactionContext transactionContext) {
        this.transactionContext = transactionContext;
    }

}
