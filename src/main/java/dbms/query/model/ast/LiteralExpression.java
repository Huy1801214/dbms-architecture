package dbms.query.model.ast;

import dbms.query.parser.TokenType;
import java.util.Collections;
import java.util.List;

public class LiteralExpression extends Expression {
    private Object value;
    private TokenType literalType;

    public LiteralExpression() {
    }

    public LiteralExpression(Object value, TokenType literalType) {
        this.value = value;
        this.literalType = literalType;
    }

    public Object getValue() {
        return value;
    }

    public TokenType getLiteralType() {
        return literalType;
    }

    @Override
    public List<AstNode> getChildren() {
        return Collections.emptyList();
    }
}
