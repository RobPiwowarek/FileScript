package lexer;

import lexer.token.Token;
import lexer.token.TokenType;

import java.io.*;
import java.util.HashMap;

public class Scanner {
    private HashMap<String, TokenType> keywordsTable;

    private InputStream source;

    private int charCounter = 1, lineCounter = 1;

    public Token getNextToken() {
        return new Token(TokenType.BOOL, "PLACEHOLDER");
    }

    public Scanner() {
        initializeKeywords();

        source = System.in;
    }

    public Scanner(File fileSource) {
        initializeKeywords();

        try {
            source = new FileInputStream(fileSource);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file for scanner source");
            throw new RuntimeException(e);
        }
    }

    private char getNextChar(){
        try {
            char nextChar = (char) source.read();

            if (nextChar == '\n') {
                ++lineCounter;

                charCounter = 1;

                return getNextChar();
            }
            else {
                ++charCounter;

                return nextChar;
            }

        } catch (IOException e) {
            System.out.println("Could not read from source");
            throw new RuntimeException(e);
        }
    }

    private void initializeKeywords() {
        keywordsTable = new HashMap<>();
        keywordsTable.put("FOREACH", TokenType.FOREACH);
        keywordsTable.put("IF", TokenType.IF);
        keywordsTable.put("ELSE", TokenType.ELSE);
        keywordsTable.put("RETURN", TokenType.RETURN);
        keywordsTable.put("=", TokenType.ASSIGN_OP);

        keywordsTable.put(":", TokenType.COLON);
        keywordsTable.put(";", TokenType.SEMICOLON);
        keywordsTable.put(".", TokenType.PERIOD);
        keywordsTable.put(",", TokenType.COMMA);

        initializeBracersKeywords();

        initializeTypeKeywords();

        initializeRelationalOperatorKeywords();
    }

    private void initializeBracersKeywords(){
        keywordsTable.put("(", TokenType.OPEN_BRACE);
        keywordsTable.put(")", TokenType.CLOSED_BRACE);

        keywordsTable.put("[", TokenType.OPEN_SQUARE_BRACE);
        keywordsTable.put("]", TokenType.CLOSED_SQUARE_BRACE);

        keywordsTable.put("{", TokenType.OPEN_CURLY_BRACE);
        keywordsTable.put("}", TokenType.CLOSED_CURLY_BRACE);
    }

    private void initializeTypeKeywords(){
        keywordsTable.put("INT", TokenType.INT);
        keywordsTable.put("STRING", TokenType.STRING);
        keywordsTable.put("TRUE", TokenType.BOOL);
        keywordsTable.put("FALSE", TokenType.BOOL);
        keywordsTable.put("FILE", TokenType.FILE);
        keywordsTable.put("CATALOGUE", TokenType.CATALOGUE);
    }

    private void initializeRelationalOperatorKeywords(){
        keywordsTable.put("!=", TokenType.RELATIONAL_OP);
        keywordsTable.put("<", TokenType.RELATIONAL_OP);
        keywordsTable.put(">", TokenType.RELATIONAL_OP);
        keywordsTable.put("<=", TokenType.RELATIONAL_OP);
        keywordsTable.put(">=", TokenType.RELATIONAL_OP);
    }
}
