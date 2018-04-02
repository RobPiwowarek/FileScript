package lexer.token;

public  class PredefinedTokens {
    public static final class Operators {
        public static final Token ASSIGN = new Token(TokenType.ASSIGN_OP, "=");
        public static final Token EQUAL = new Token(TokenType.RELATIONAL_OP, "==");
        public static final Token NOT_EQUAL = new Token(TokenType.RELATIONAL_OP, "!=");
        public static final Token LESS = new Token(TokenType.RELATIONAL_OP, "<");
        public static final Token LESS_EQUAL = new Token(TokenType.RELATIONAL_OP, "<=");
        public static final Token GREATER = new Token(TokenType.RELATIONAL_OP, ">");
        public static final Token GREATER_EQUAL = new Token(TokenType.RELATIONAL_OP, ">=");
    }

    public static final class Bracers {
        public static final Token OPEN_BRACE = new Token(TokenType.OPEN_BRACE, "(");
        public static final Token CLOSED_BRACE = new Token(TokenType.CLOSED_BRACE, ")");
        public static final Token OPEN_CURLY_BRACE = new Token(TokenType.OPEN_CURLY_BRACE, "{");
        public static final Token CLOSED_CURLY_BRACE = new Token(TokenType.CLOSED_CURLY_BRACE, "}");
        public static final Token OPEN_SQUARE_BRACE = new Token(TokenType.OPEN_SQUARE_BRACE, "[");
        public static final Token CLOSED_SQUARE_BRACE = new Token(TokenType.CLOSED_SQUARE_BRACE, "]");
    }

    public static final class Others {
        public static final Token SEMICOLON = new Token(TokenType.SEMICOLON, ";");
        public static final Token COLON = new Token(TokenType.COLON, ":");
        public static final Token PERIOD = new Token(TokenType.PERIOD, ".");
        public static final Token COMMA = new Token(TokenType.COMMA, ",");
    }
}
