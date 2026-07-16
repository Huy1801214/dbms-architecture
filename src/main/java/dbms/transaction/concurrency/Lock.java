package dbms.transaction.concurrency;

public class Lock {

    private String resourceId;
    private LockType type;

    public Lock() {
    }

    public Lock(String resourceId, LockType type) {
        this.resourceId = resourceId;
        this.type = type;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public LockType getType() {
        return type;
    }

    public void setType(LockType type) {
        this.type = type;
    }

}
