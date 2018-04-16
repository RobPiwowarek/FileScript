package lexer;

import lexer.exception.EmptySourceException;
import lexer.token.Token;
import lexer.token.TokenType;

import java.io.ByteArrayInputStream;
import java.io.File;

public class Scanner {
    private Source source;

    public Scanner() {
        source = new Source(System.in);
    }

    public Scanner(File fileSource) {
        source = new Source(fileSource);
    }

    public Scanner(String stringSource) { source = new Source(new ByteArrayInputStream(stringSource.getBytes())); }

    Token getNextToken() {
        if (source.isEoF())
            throw new EmptySourceException();

        char currentChar = source.nextChar();
        Token token;

        if (Character.isWhitespace(currentChar))
            do {
                currentChar = source.nextChar();
            } while(Character.isWhitespace(currentChar) && !source.isEoF());

        if (source.isEoF())
            return EndOfFileToken();

        if (Character.isDigit(currentChar))
            token = processNumberOrDate(currentChar);
        else if (Character.isLetter(currentChar))
            token = processKeywordOrIdentifier(currentChar);
        else if (currentChar == '&' || currentChar == '|')
            token = processLogicalOperator(currentChar);
        else if (currentChar == '=' || currentChar == '!' || currentChar == '<' || currentChar == '>')
            token = processRelationalOrAssignmentOperator(currentChar);
        else if (currentChar == '\"')
            token = processString(currentChar);
        else
            token = processSingleCharacterToken(currentChar);

        return token;
    }

    private Token ErrorToken(char currentChar) {
        return new Token(TokenType.EMPTY, "Unexpected character " + currentChar + " at " + source.getLineCounter() + ":" + source.getCharCounter());
    }

    private Token EndOfFileToken(){
        return new Token(TokenType.EOF, "No more tokens");
    }

    private Token processString(char currentChar) {
        StringBuilder token = new StringBuilder();

        token.append(currentChar);

        while (source.peek() != '\"' && !source.isEoF())
            token.append(source.nextChar());

        if (!source.isEoF())
            token.append(source.nextChar());

        return new Token(TokenType.CONST_STRING, token.toString());
    }

    private Token processNumberOrDate(char currentChar) {
        StringBuilder token = new StringBuilder();

        token.append(currentChar);

        while (Character.isDigit(source.peek()) && !source.isEoF()) {
            token.append(source.nextChar());
        }

        return new Token(TokenType.CONST_INT, token.toString());
    }

    private Token processKeywordOrIdentifier(char currentChar) {
        StringBuilder token = new StringBuilder();

        token.append(currentChar);

        while (Character.isLetter(source.peek()) || Character.isDigit(source.peek()) && !source.isEoF()) {
            token.append(source.nextChar());
        }

        if (KeywordsTable.get(token.toString()) != null) {
            return KeywordsTable.get(token.toString());
        } else {
            return new Token(TokenType.IDENTIFIER, token.toString());
        }
    }

    private Token processLogicalOperator(char currentChar) {
        StringBuilder token = new StringBuilder();
        
        token.append(currentChar);
        
        if (source.peek() == '&' && currentChar == '&' || source.peek() == '|' && currentChar == '|'){
            token.append(source.nextChar());
        }
        else 
            return ErrorToken(source.peek());
        
        return KeywordsTable.get(token.toString());
    }
    
    private Token processRelationalOrAssignmentOperator(char currentChar) {
        StringBuilder token = new StringBuilder();

        token.append(currentChar);

        if (source.peek() == '=') {
            token.append(source.nextChar());
        }
        else {
            if (currentChar == '!')
                return ErrorToken(source.peek());
        }

        return KeywordsTable.get(token.toString());
    }

    private Token processSingleCharacterToken(char currentChar) {
        Token token = KeywordsTable.get(Character.toString(currentChar));

        if (token == null)
            return ErrorToken(currentChar);
        else
            return token;
    }
}