package dbms.query.model.ast;

import java.util.List;

public interface AstNode {
    List<AstNode> getChildren();
}
