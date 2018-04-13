package lexer;

import lexer.exception.EmptySourceException;
import lexer.token.PredefinedTokens;
import lexer.token.Token;
import lexer.token.TokenType;

import java.io.*;
import java.util.HashMap;

// fixme: moglbym wszystkiemu dac sprawdzenie czy nie ma juz EOF
public class Scanner {
    private HashMap<String, Token> keywordsTable;

    private Source source;

    public Scanner() {
        initializeKeywords();

        source = new Source(System.in);
    }

    public Scanner(File fileSource) {
        initializeKeywords();

        source = new Source(fileSource);
    }

    private Token getNextToken() {
        if (source.isEoF())
            throw new EmptySourceException();

        char currentChar = source.nextChar();
        Token token;

        while (Character.isWhitespace(source.peek()) || Character.isWhitespace(currentChar))
            currentChar = source.nextChar();

        if (Character.isDigit(currentChar))
            token = processNumberOrDate(currentChar);
        else if (Character.isLetter(currentChar))
            token = processKeywordOrIdentifier(currentChar);
        else if (currentChar == '=' || currentChar == '!' || currentChar == '<' || currentChar == '>')
            token = processRelationalOperator(currentChar);
        else if (currentChar == '+' || currentChar == '-')
            token = processArithmeticOperator(currentChar);
        else if (currentChar == '\"')
            token = processString(currentChar);
        else {
            switch (currentChar) {
                case ';':
                    return PredefinedTokens.Others.SEMICOLON;
                case ':':
                    return PredefinedTokens.Others.COLON;
                case '.':
                    return PredefinedTokens.Others.PERIOD;
                case ',':
                    return PredefinedTokens.Others.COMMA;
                default:
                    token = ErrorToken(currentChar);
            }
        }

        if (token.getType() == TokenType.EMPTY){
            // todo: throw/handle parsing exception?
        }

        return token;
    }

    private Token ErrorToken(char currentChar) {
        return new Token(TokenType.EMPTY, "Unexpected character " + currentChar + " at " + source.getLineCounter() + ":" + source.getCharCounter());
    }

    private Token processString(char currentChar) {
        StringBuilder token = new StringBuilder();

        token.append(currentChar);

        while(source.peek() != '\"' && !source.isEoF())
            token.append(source.nextChar());

        token.append(source.nextChar());

        return new Token(TokenType.CONST_CHAR, token.toString());
    }

    private Token processNumberOrDate(char currentChar) {
        StringBuilder token = new StringBuilder();

        token.append(currentChar);

        while (Character.isDigit(source.peek()) && !source.isEoF()) {
            token.append(source.nextChar());
        }

        if (source.peek() == '/' && token.length() == 2)
            return processDate(token);

        return new Token(TokenType.CONST_INT, token.toString());
    }

    private Token processKeywordOrIdentifier(char currentChar) {
        StringBuilder token = new StringBuilder();

        token.append(currentChar);

        while (Character.isLetter(source.peek()) || Character.isDigit(source.peek()) && !source.isEoF()){
            token.append(source.nextChar());
        }

        if (keywordsTable.containsKey(token.toString())){
            return keywordsTable.get(token.toString());
        }
        else {
            return new Token(TokenType.IDENTIFIER, token.toString());
        }
    }

    private Token processRelationalOperator(char currentChar) {
        StringBuilder token = new StringBuilder();

        token.append(currentChar);

        if (source.peek() == '=') {
            token.append(source.nextChar());
        }
        else
            return ErrorToken(source.peek());

        return keywordsTable.get(token.toString());
    }

    private Token processArithmeticOperator(char currentChar) {
        StringBuilder token = new StringBuilder();

        token.append(currentChar);

        if (source.peek() == '=') {
            token.append(source.nextChar());
        }
        else
            return ErrorToken(source.peek());

        return keywordsTable.get(token.toString());
    }

    private Token processDate(StringBuilder token) {
        token.append(source.nextChar());

        if (Character.isDigit(source.peek()) && !source.isEoF())
            token.append(source.nextChar());
        else
            return ErrorToken(source.peek());

        if (Character.isDigit(source.peek()) && !source.isEoF())
            token.append(source.nextChar());
        else
            return ErrorToken(source.peek());

        if (source.peek() == '/' && !source.isEoF())
            token.append(source.nextChar());
        else
            return ErrorToken(source.peek());

        if (Character.isDigit(source.peek()) && !source.isEoF())
            token.append(source.nextChar());
        else
            return ErrorToken(source.peek());

        if (Character.isDigit(source.peek()) && !source.isEoF())
            token.append(source.nextChar());
        else
            return ErrorToken(source.peek());

        if (Character.isDigit(source.peek()) && !source.isEoF())
            token.append(source.nextChar());
        else
            return ErrorToken(source.peek());

        if (Character.isDigit(source.peek()) && !source.isEoF())
            token.append(source.nextChar());
        else
            return ErrorToken(source.peek());

        return new Token(TokenType.CONST_DATE, token.toString());
    }

    private void initializeKeywords() {
        keywordsTable = new HashMap<>();
        keywordsTable.put("foreach", PredefinedTokens.Others.FOREACH);
        keywordsTable.put("if", PredefinedTokens.Others.IF);
        keywordsTable.put("else", PredefinedTokens.Others.ELSE);
        keywordsTable.put("return", PredefinedTokens.Others.RETURN);
        keywordsTable.put("=", PredefinedTokens.Operators.ASSIGN);

        keywordsTable.put("true", PredefinedTokens.Constants.TRUE);
        keywordsTable.put("false", PredefinedTokens.Constants.FALSE);

        initializeArithmeticOperators();
        initializeBracersKeywords();
        initializeTypeKeywords();
        initializeRelationalOperatorKeywords();
    }

    private void initializeBracersKeywords() {
        keywordsTable.put("(", PredefinedTokens.Bracers.OPEN_BRACE);
        keywordsTable.put(")", PredefinedTokens.Bracers.CLOSED_BRACE);

        keywordsTable.put("[", PredefinedTokens.Bracers.OPEN_SQUARE_BRACE);
        keywordsTable.put("]", PredefinedTokens.Bracers.CLOSED_SQUARE_BRACE);

        keywordsTable.put("{", PredefinedTokens.Bracers.OPEN_CURLY_BRACE);
        keywordsTable.put("}", PredefinedTokens.Bracers.CLOSED_CURLY_BRACE);
    }

    private void initializeTypeKeywords() {
        keywordsTable.put("Int", PredefinedTokens.Types.INT);
        keywordsTable.put("String", PredefinedTokens.Types.STRING);
        keywordsTable.put("Bool", PredefinedTokens.Types.BOOL);
        keywordsTable.put("File", PredefinedTokens.Types.FILE);
        keywordsTable.put("CATALOGUE", PredefinedTokens.Types.CATALOGUE);
        keywordsTable.put("Date", PredefinedTokens.Types.DATE);
    }

    private void initializeRelationalOperatorKeywords() {
        keywordsTable.put("!=", PredefinedTokens.Operators.Relational.NOT_EQUAL);
        keywordsTable.put("<", PredefinedTokens.Operators.Relational.LESS);
        keywordsTable.put(">", PredefinedTokens.Operators.Relational.GREATER);
        keywordsTable.put("<=", PredefinedTokens.Operators.Relational.LESS_EQUAL);
        keywordsTable.put(">=", PredefinedTokens.Operators.Relational.GREATER_EQUAL);
        keywordsTable.put("==", PredefinedTokens.Operators.Relational.EQUAL);
    }

    private void initializeArithmeticOperators() {
        keywordsTable.put("+=", PredefinedTokens.Operators.Arithmetic.PLUS_EQUAL);
        keywordsTable.put("-=", PredefinedTokens.Operators.Arithmetic.MINUS_EQUAL);
        keywordsTable.put("+", PredefinedTokens.Operators.Arithmetic.PLUS);
        keywordsTable.put("-", PredefinedTokens.Operators.Arithmetic.MINUS);
    }
}
