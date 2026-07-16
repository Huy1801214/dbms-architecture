package dbms.query.parser;

import java.util.ArrayList;
import java.util.List;

public abstract class ASTNode {

    private List<ASTNode> children;

    public ASTNode() {
        this.children = new ArrayList<>();
    }

    public ASTNode(List<ASTNode> children) {
        this.children = children;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public void setChildren(List<ASTNode> children) {
        this.children = children;
    }

}
