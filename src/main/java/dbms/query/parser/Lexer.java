package dbms.query.parser;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String input;
    private int position;
    private final StringBuilder buffer = new StringBuilder();
    private final List<Token> tokens = new ArrayList<>();
    private LexerState currentState;

    public List<Token> tokenize(String sql) {
        return null;
    }

    public void setState(LexerState state) {
        this.currentState = state;
    }

    public char currentCharacter() {
        return ' ';
    }

    public char peekCharacter() {
        return ' ';
    }

    public void advance() {
    }

    public boolean isEnd() {
        return false;
    }

    public void appendCurrentCharacter() {
    }

    public void emitToken(TokenType type) {
    }

    public void emitToken(TokenType type, String value) {
    }

    public void clearBuffer() {
    }

    public String getBufferValue() {
        return "";
    }
}
