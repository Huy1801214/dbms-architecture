package dbms.query.model.ast;

import java.util.Collections;
import java.util.List;

public class ColumnReference extends Expression {
    private String columnName;
    private String tableAlias;

    public ColumnReference() {
    }

    public ColumnReference(String columnName, String tableAlias) {
        this.columnName = columnName;
        this.tableAlias = tableAlias;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    @Override
    public List<AstNode> getChildren() {
        return Collections.emptyList();
    }
}
