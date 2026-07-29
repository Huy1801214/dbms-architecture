package dbms.query.model.ast;

import java.util.List;

public abstract class Expression implements AstNode {
    @Override
    public abstract List<AstNode> getChildren();
}
