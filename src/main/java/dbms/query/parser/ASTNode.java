package dbms.query.parser;

import java.util.ArrayList;
import java.util.List;

public abstract class ASTNode {

    private String nodeType;
    private List<ASTNode> children;

    public ASTNode() {
        this.children = new ArrayList<>();
    }

    public ASTNode(String nodeType, List<ASTNode> children) {
        this.nodeType = nodeType;
        this.children = children;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public void setChildren(List<ASTNode> children) {
        this.children = children;
    }

}
