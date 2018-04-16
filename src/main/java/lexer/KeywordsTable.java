package lexer;

import lexer.token.PredefinedTokens;
import lexer.token.Token;

import java.util.HashMap;

class KeywordsTable {
    private static HashMap<String, Token> keywordsTable;

    static {
        keywordsTable = new HashMap<>();
        initializeKeywords();
    }

    static Token get(String key){
        return keywordsTable.get(key);
    }

    private static void initializeKeywords() {
        keywordsTable = new HashMap<>();
        keywordsTable.put("foreach", PredefinedTokens.Others.FOREACH);
        keywordsTable.put("if", PredefinedTokens.Others.IF);
        keywordsTable.put("else", PredefinedTokens.Others.ELSE);
        keywordsTable.put("return", PredefinedTokens.Others.RETURN);
        keywordsTable.put("=", PredefinedTokens.Operators.ASSIGN);
        keywordsTable.put("def", PredefinedTokens.Others.DEF);

        keywordsTable.put("true", PredefinedTokens.Constants.TRUE);
        keywordsTable.put("false", PredefinedTokens.Constants.FALSE);

        keywordsTable.put(".", PredefinedTokens.Others.PERIOD);
        keywordsTable.put(",", PredefinedTokens.Others.COMMA);
        keywordsTable.put(";", PredefinedTokens.Others.SEMICOLON);
        keywordsTable.put(":", PredefinedTokens.Others.COLON);

        initializeArithmeticOperatorKeywords();
        initializeBracersKeywords();
        initializeTypeKeywords();
        initializeRelationalOperatorKeywords();
        initializeLogicalOperatorKeywords();
    }

    private static void initializeLogicalOperatorKeywords() {
        keywordsTable.put("&&", PredefinedTokens.Operators.Logical.AND);
        keywordsTable.put("||", PredefinedTokens.Operators.Logical.OR);
    }

    private static void initializeBracersKeywords() {
        keywordsTable.put("(", PredefinedTokens.Bracers.OPEN_BRACE);
        keywordsTable.put(")", PredefinedTokens.Bracers.CLOSED_BRACE);

        keywordsTable.put("[", PredefinedTokens.Bracers.OPEN_SQUARE_BRACE);
        keywordsTable.put("]", PredefinedTokens.Bracers.CLOSED_SQUARE_BRACE);

        keywordsTable.put("{", PredefinedTokens.Bracers.OPEN_CURLY_BRACE);
        keywordsTable.put("}", PredefinedTokens.Bracers.CLOSED_CURLY_BRACE);
    }

    private static void initializeTypeKeywords() {
        keywordsTable.put("int", PredefinedTokens.Types.INT);
        keywordsTable.put("string", PredefinedTokens.Types.STRING);
        keywordsTable.put("bool", PredefinedTokens.Types.BOOL);
        keywordsTable.put("file", PredefinedTokens.Types.FILE);
        keywordsTable.put("catalogue", PredefinedTokens.Types.CATALOGUE);
        keywordsTable.put("date", PredefinedTokens.Types.DATE);
    }

    private static void initializeRelationalOperatorKeywords() {
        keywordsTable.put("!=", PredefinedTokens.Operators.Relational.NOT_EQUAL);
        keywordsTable.put("<", PredefinedTokens.Operators.Relational.LESS);
        keywordsTable.put(">", PredefinedTokens.Operators.Relational.GREATER);
        keywordsTable.put("<=", PredefinedTokens.Operators.Relational.LESS_EQUAL);
        keywordsTable.put(">=", PredefinedTokens.Operators.Relational.GREATER_EQUAL);
        keywordsTable.put("==", PredefinedTokens.Operators.Relational.EQUAL);
    }

    private static void initializeArithmeticOperatorKeywords() {
        keywordsTable.put("*", PredefinedTokens.Operators.Arithmetic.MULTIPLY);
        keywordsTable.put("/", PredefinedTokens.Operators.Arithmetic.DIVIDE);
        keywordsTable.put("+", PredefinedTokens.Operators.Arithmetic.PLUS);
        keywordsTable.put("-", PredefinedTokens.Operators.Arithmetic.MINUS);
    }
}