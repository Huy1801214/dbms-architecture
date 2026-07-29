package dbms.query.model;

import dbms.query.service.PhysicalOperator;
import java.util.ArrayList;
import java.util.List;

public class PhysicalPlan {
    private final List<PhysicalOperator> operators = new ArrayList<>();

    public List<PhysicalOperator> getOperators() {
        return operators;
    }
}
