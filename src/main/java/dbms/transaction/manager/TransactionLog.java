package dbms.transaction.manager;

import java.util.ArrayList;
import java.util.List;

public class TransactionLog {

    private List<Transaction> transactions;

    public TransactionLog() {
        this.transactions = new ArrayList<>();
    }

    public TransactionLog(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
