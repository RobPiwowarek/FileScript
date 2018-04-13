package lexer.token;

public  class PredefinedTokens {
    public static final class Operators {
        public static final Token ASSIGN = new Token(TokenType.ASSIGN_OP, "=");

        public static final class Relational {
            public static final Token EQUAL = new Token(TokenType.RELATIONAL_OP, "==");
            public static final Token NOT_EQUAL = new Token(TokenType.RELATIONAL_OP, "!=");
            public static final Token LESS = new Token(TokenType.RELATIONAL_OP, "<");
            public static final Token LESS_EQUAL = new Token(TokenType.RELATIONAL_OP, "<=");
            public static final Token GREATER = new Token(TokenType.RELATIONAL_OP, ">");
            public static final Token GREATER_EQUAL = new Token(TokenType.RELATIONAL_OP, ">=");
        }

        public static final class Arithmetic {
            public static final Token PLUS = new Token(TokenType.ARITHMETIC_OP, "+");
            public static final Token MINUS = new Token(TokenType.ARITHMETIC_OP, "-");
            public static final Token PLUS_EQUAL = new Token(TokenType.ARITHMETIC_OP, "+=");
            public static final Token MINUS_EQUAL = new Token(TokenType.ARITHMETIC_OP, "-=");
        }
    }

    public static final class Bracers {
        public static final Token OPEN_BRACE = new Token(TokenType.OPEN_BRACE, "(");
        public static final Token CLOSED_BRACE = new Token(TokenType.CLOSED_BRACE, ")");
        public static final Token OPEN_CURLY_BRACE = new Token(TokenType.OPEN_CURLY_BRACE, "{");
        public static final Token CLOSED_CURLY_BRACE = new Token(TokenType.CLOSED_CURLY_BRACE, "}");
        public static final Token OPEN_SQUARE_BRACE = new Token(TokenType.OPEN_SQUARE_BRACE, "[");
        public static final Token CLOSED_SQUARE_BRACE = new Token(TokenType.CLOSED_SQUARE_BRACE, "]");
    }

    public static final class Types {
        public static final Token DATE = new Token(TokenType.DATE, "Date");
        public static final Token INT = new Token(TokenType.INT, "Int");
        public static final Token BOOL = new Token(TokenType.BOOL, "Bool");
        public static final Token FILE = new Token(TokenType.FILE, "File");
        public static final Token CATALOGUE = new Token(TokenType.CATALOGUE, "Catalogue");
        public static final Token STRING = new Token(TokenType.STRING, "String");
    }

    public static final class Constants {
        public static final Token TRUE = new Token(TokenType.CONST_BOOL, "true");
        public static final Token FALSE = new Token(TokenType.CONST_BOOL, "false");
    }

    public static final class Others {
        public static final Token SEMICOLON = new Token(TokenType.SEMICOLON, ";");
        public static final Token COLON = new Token(TokenType.COLON, ":");
        public static final Token PERIOD = new Token(TokenType.PERIOD, ".");
        public static final Token COMMA = new Token(TokenType.COMMA, ",");
        public static final Token VAR = new Token(TokenType.VAR, "var");
        public static final Token IF = new Token(TokenType.IF, "if");
        public static final Token ELSE = new Token(TokenType.ELSE, "else");
        public static final Token FOREACH = new Token(TokenType.FOREACH, "foreach");
        public static final Token RETURN = new Token(TokenType.RETURN, "return");
        public static final Token EMPTY = new Token(TokenType.EMPTY, "<EMPTY>");
    }
}
