package dbms.transaction.concurrency;

public class Lock {

    private String type;

    public Lock() {
    }

    public Lock(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
