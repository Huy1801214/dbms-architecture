package dbms.query.model.ast;

import java.util.ArrayList;
import java.util.List;

public class SelectStatement extends Statement {
    private List<Expression> selectItems;
    private TableReference from;
    private Expression where;

    public SelectStatement() {
    }

    public SelectStatement(List<Expression> selectItems, TableReference from, Expression where) {
        this.selectItems = selectItems;
        this.from = from;
        this.where = where;
    }

    public List<Expression> getSelectItems() {
        return selectItems;
    }

    public TableReference getFrom() {
        return from;
    }

    public Expression getWhere() {
        return where;
    }

    @Override
    public List<AstNode> getChildren() {
        List<AstNode> children = new ArrayList<>();
        if (selectItems != null) children.addAll(selectItems);
        if (from != null) children.add(from);
        if (where != null) children.add(where);
        return children;
    }
}
