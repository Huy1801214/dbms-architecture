package dbms.transaction.manager;

public class Transaction {

    private int transactionId;
    private TransactionState state;
    private TransactionContext context;

    public Transaction() {
    }

    public Transaction(int transactionId,
            TransactionState state,
            TransactionContext context) {
        this.transactionId = transactionId;
        this.state = state;
        this.context = context;
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

    public TransactionContext getContext() {
        return context;
    }

    public void setContext(TransactionContext context) {
        this.context = context;
    }

}
