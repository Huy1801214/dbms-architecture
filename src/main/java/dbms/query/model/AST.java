package dbms.query.model;

import dbms.query.model.ast.AstNode;

public class AST {
    private AstNode root;

    public AST() {
    }

    public AST(AstNode root) {
        this.root = root;
    }

    public AstNode getRoot() {
        return root;
    }

    public void setRoot(AstNode root) {
        this.root = root;
    }
}
