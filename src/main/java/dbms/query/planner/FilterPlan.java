package dbms.query.planner;

public class FilterPlan extends ExecutionPlan {

    private String predicate;

    public FilterPlan() {
    }

    public FilterPlan(String predicate) {
        this.predicate = predicate;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

}
