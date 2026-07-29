package dbms.query.model;

import java.util.UUID;

public class LogicalJoin extends LogicalOperator {
    private final UUID leftTableId;
    private final UUID rightTableId;
    private final Object condition;

    public LogicalJoin(UUID leftTableId, UUID rightTableId, Object condition) {
        this.leftTableId = leftTableId;
        this.rightTableId = rightTableId;
        this.condition = condition;
    }

    public UUID getLeftTableId() {
        return leftTableId;
    }

    public UUID getRightTableId() {
        return rightTableId;
    }

    public Object getCondition() {
        return condition;
    }

    @Override
    public LogicalOperatorType getType() {
        return LogicalOperatorType.JOIN;
    }
}
