package dbms.query.planner;

public class SequentialScanPlan extends ExecutionPlan {

    private String tableName;

    public SequentialScanPlan() {
    }

    public SequentialScanPlan(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
