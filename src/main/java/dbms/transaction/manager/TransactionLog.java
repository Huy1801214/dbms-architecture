package dbms.transaction.manager;

import java.util.ArrayList;
import java.util.List;

public class TransactionLog {

    private List<String> logEntries;

    public TransactionLog() {
        this.logEntries = new ArrayList<>();
    }

    public TransactionLog(List<String> logEntries) {
        this.logEntries = logEntries;
    }

    public List<String> getLogEntries() {
        return logEntries;
    }

    public void setLogEntries(List<String> logEntries) {
        this.logEntries = logEntries;
    }

}
