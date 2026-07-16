package dbms.query.parser;

public class SQLParser {

    private Lexer lexer;
    private Parser parser;

    public SQLParser() {
    }

    public SQLParser(Lexer lexer, Parser parser) {
        this.lexer = lexer;
        this.parser = parser;
    }

    public Lexer getLexer() {
        return lexer;
    }

    public void setLexer(Lexer lexer) {
        this.lexer = lexer;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public ASTNode parse(String sql) {
        return null;
    }

}
