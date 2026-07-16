package dbms.query.parser;

public class SyntaxError extends Exception {

    public SyntaxError() {
    }

    public SyntaxError(String message) {
        super(message);
    }

}
