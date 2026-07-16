package dbms.transaction.manager;

public class TransactionContext {

    private Transaction transaction;

    public TransactionContext() {
    }

    public TransactionContext(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

}
