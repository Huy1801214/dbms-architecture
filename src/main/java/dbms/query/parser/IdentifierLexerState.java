package dbms.query.parser;

import java.util.Set;

public class IdentifierLexerState implements LexerState {
    private static final Set<String> KEYWORDS = Set.of(
            "SELECT", "FROM", "WHERE", "INSERT", "UPDATE", "DELETE", "JOIN",
            "INTO", "VALUES", "SET", "CREATE", "TABLE", "DROP", "AND", "OR",
            "NOT", "NULL", "PRIMARY", "KEY", "FOREIGN", "REFERENCES", "UNIQUE",
            "CHECK", "INDEX", "SCHEMA", "DATABASE", "VIEW", "SEQUENCE", "PROCEDURE",
            "AS", "ON", "BY", "ORDER", "GROUP", "HAVING", "LIMIT", "INNER", "LEFT", "RIGHT");

    private static final Set<String> BOOLEANS = Set.of("TRUE", "FALSE");

    @Override
    public void consume(Lexer context) {
    }
}
