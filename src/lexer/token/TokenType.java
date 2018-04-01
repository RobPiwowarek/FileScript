package lexer.token;

public enum TokenType {
    INT, BOOL, STRING, FILE, CATALOGUE,
    ASSIGN_OP,
    RELATIONAL_OP,
    IF, ELSE, FOREACH,
    CONST_INT, CONST_CHAR, TRUE, FALSE,
    IDENTIFIER,
    COMMA, PERIOD, SEMICOLON, COLON,
    RETURN,
    OPEN_BRACE, CLOSED_BRACE, OPEN_CURLY_BRACE, CLOSED_CURLY_BRACE, OPEN_SQUARE_BRACE, CLOSED_SQUARE_BRACE
}