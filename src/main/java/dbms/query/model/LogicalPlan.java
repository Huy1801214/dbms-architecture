package dbms.query.model;

import java.util.ArrayList;
import java.util.List;

public class LogicalPlan {
    private final List<LogicalOperator> operators = new ArrayList<>();

    public List<LogicalOperator> getOperators() {
        return operators;
    }
}
