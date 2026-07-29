package dbms.query;

import dbms.query.parser.Lexer;
import dbms.query.parser.Token;
import dbms.query.parser.TokenType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LexerTest {

    private Lexer lexer;

    @BeforeEach
    public void setUp() {
        lexer = new Lexer();
    }

    @Test
    public void shouldTokenizeSQLStatement() {
        List<Token> tokens = lexer.tokenize("SELECT id FROM users");
        assertEquals(5, tokens.size());
        assertEquals(TokenType.KEYWORD, tokens.get(0).getType());
        assertEquals("SELECT", tokens.get(0).getValue());
        assertEquals(TokenType.IDENTIFIER, tokens.get(1).getType());
        assertEquals("id", tokens.get(1).getValue());
        assertEquals(TokenType.KEYWORD, tokens.get(2).getType());
        assertEquals("FROM", tokens.get(2).getValue());
        assertEquals(TokenType.IDENTIFIER, tokens.get(3).getType());
        assertEquals("users", tokens.get(3).getValue());
        assertEquals(TokenType.END_OF_FILE, tokens.get(4).getType());
    }

    @Test
    public void shouldIgnoreWhitespace() {
        List<Token> tokens = lexer.tokenize("   SELECT   name   ");
        assertEquals(3, tokens.size());
        assertEquals("SELECT", tokens.get(0).getValue());
        assertEquals("name", tokens.get(1).getValue());
        assertEquals(TokenType.END_OF_FILE, tokens.get(2).getType());
    }

    @Test
    public void shouldIgnoreComments() {
        List<Token> tokens = lexer.tokenize("SELECT -- comment\n name");
        assertEquals(3, tokens.size());
        assertEquals("SELECT", tokens.get(0).getValue());
        assertEquals("name", tokens.get(1).getValue());
        assertEquals(TokenType.END_OF_FILE, tokens.get(2).getType());
    }

    @Test
    public void shouldRecognizeKeywords() {
        List<Token> tokens = lexer.tokenize("SELECT FROM WHERE INSERT UPDATE DELETE");
        assertEquals(7, tokens.size());
        for (int i = 0; i < 6; i++) {
            assertEquals(TokenType.KEYWORD, tokens.get(i).getType());
        }
        assertEquals(TokenType.END_OF_FILE, tokens.get(6).getType());
    }

    @Test
    public void shouldRecognizeIdentifiers() {
        List<Token> tokens = lexer.tokenize("user_id user_name");
        assertEquals(3, tokens.size());
        assertEquals(TokenType.IDENTIFIER, tokens.get(0).getType());
        assertEquals("user_id", tokens.get(0).getValue());
        assertEquals(TokenType.IDENTIFIER, tokens.get(1).getType());
        assertEquals("user_name", tokens.get(1).getValue());
        assertEquals(TokenType.END_OF_FILE, tokens.get(2).getType());
    }

    @Test
    public void shouldRecognizeOperators() {
        List<Token> tokens = lexer.tokenize("age >= 18");
        assertEquals(4, tokens.size());
        assertEquals(TokenType.IDENTIFIER, tokens.get(0).getType());
        assertEquals(TokenType.OPERATOR, tokens.get(1).getType());
        assertEquals(">=", tokens.get(1).getValue());
        assertEquals(TokenType.NUMBER, tokens.get(2).getType());
        assertEquals(TokenType.END_OF_FILE, tokens.get(3).getType());
    }

    @Test
    public void shouldRecognizeNumbers() {
        List<Token> tokens = lexer.tokenize("100 3.14");
        assertEquals(3, tokens.size());
        assertEquals(TokenType.NUMBER, tokens.get(0).getType());
        assertEquals("100", tokens.get(0).getValue());
        assertEquals(TokenType.NUMBER, tokens.get(1).getType());
        assertEquals("3.14", tokens.get(1).getValue());
        assertEquals(TokenType.END_OF_FILE, tokens.get(2).getType());
    }

    @Test
    public void shouldRecognizeStringLiteral() {
        List<Token> tokens = lexer.tokenize("'Hello World'");
        assertEquals(2, tokens.size());
        assertEquals(TokenType.STRING, tokens.get(0).getType());
        assertEquals("Hello World", tokens.get(0).getValue());
        assertEquals(TokenType.END_OF_FILE, tokens.get(1).getType());
    }

    @Test
    public void shouldRecognizeBooleanLiteral() {
        List<Token> tokens = lexer.tokenize("TRUE FALSE");
        assertEquals(3, tokens.size());
        assertEquals(TokenType.BOOLEAN, tokens.get(0).getType());
        assertEquals("TRUE", tokens.get(0).getValue());
        assertEquals(TokenType.BOOLEAN, tokens.get(1).getType());
        assertEquals("FALSE", tokens.get(1).getValue());
        assertEquals(TokenType.END_OF_FILE, tokens.get(2).getType());
    }

    @Test
    public void shouldRecognizeDelimiter() {
        List<Token> tokens = lexer.tokenize("(id, name);");
        assertEquals(7, tokens.size());
        assertEquals(TokenType.DELIMITER, tokens.get(0).getType());
        assertEquals("(", tokens.get(0).getValue());
        assertEquals(TokenType.IDENTIFIER, tokens.get(1).getType());
        assertEquals("id", tokens.get(1).getValue());
        assertEquals(TokenType.DELIMITER, tokens.get(2).getType());
        assertEquals(",", tokens.get(2).getValue());
        assertEquals(TokenType.IDENTIFIER, tokens.get(3).getType());
        assertEquals("name", tokens.get(3).getValue());
        assertEquals(TokenType.DELIMITER, tokens.get(4).getType());
        assertEquals(")", tokens.get(4).getValue());
        assertEquals(TokenType.DELIMITER, tokens.get(5).getType());
        assertEquals(";", tokens.get(5).getValue());
        assertEquals(TokenType.END_OF_FILE, tokens.get(6).getType());
    }
}
