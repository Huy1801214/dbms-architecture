package dbms.query.model.ast;

import java.util.Collections;
import java.util.List;

public class TableReference implements AstNode {
    private String tableName;
    private String alias;

    public TableReference() {
    }

    public TableReference(String tableName, String alias) {
        this.tableName = tableName;
        this.alias = alias;
    }

    public String getTableName() {
        return tableName;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public List<AstNode> getChildren() {
        return Collections.emptyList();
    }
}
