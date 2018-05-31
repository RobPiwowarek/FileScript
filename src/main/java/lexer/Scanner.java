package lexer;

import lexer.exception.EmptySourceException;
import lexer.token.Token;
import lexer.token.TokenType;

import java.io.ByteArrayInputStream;
import java.io.File;

public class Scanner {
    private Source source;
    private Token currentToken;

    public Scanner() {
        source = new Source(System.in);
        getNextToken();
    }

    public Scanner(File fileSource) {
        source = new Source(fileSource);
        getNextToken();
    }

    public Scanner(String stringSource) {
        source = new Source(new ByteArrayInputStream(stringSource.getBytes()));
        getNextToken();
    }

    private void getNextToken() {
        try {
            char currentChar = source.nextChar();

            if (Character.isWhitespace(currentChar))
                do {
                    currentChar = source.nextChar();
                } while (Character.isWhitespace(currentChar));

            if (Character.isDigit(currentChar) && currentChar != '0') // todo: a co z zerem jako liczba
                currentToken = processNumber(currentChar);
            else if (Character.isLetter(currentChar))
                currentToken = processKeywordOrIdentifier(currentChar);
            else if (currentChar == '&' || currentChar == '|')
                currentToken = processLogicalOperator(currentChar);
            else if (currentChar == '=' || currentChar == '!' || currentChar == '<' || currentChar == '>')
                currentToken = processRelationalOrAssignmentOperator(currentChar);
            else if (currentChar == '\"')
                currentToken = processString(currentChar);
            else
                currentToken = processSingleCharacterToken(currentChar);
        }
        catch (EmptySourceException e) {
            currentToken = EndOfFileToken();
        }
    }

    public Token nextToken() {
        Token tempToken = currentToken;

        if (currentToken.getType() == TokenType.EOF)
            return tempToken;
        else
            getNextToken();

        return tempToken;
    }

    public Token peek() {
        return currentToken;
    }

    public int getLineNumber() {
        return source.getLineCounter();
    }

    public int getPositionInLine() {
        return source.getCharCounter();
    }

    private Token ErrorToken(char currentChar) {
        return new Token(TokenType.EMPTY, "Unexpected character " + currentChar + " at " + source.getLineCounter() + ":" + source.getCharCounter());
    }

    private Token EndOfFileToken() {
        return new Token(TokenType.EOF, "No more tokens");
    }

    private Token processString(char currentChar) throws EmptySourceException{
        StringBuilder token = new StringBuilder();

        while (!source.isEoF()) {
            if ((source.peek() == '\"' && token.charAt(token.length() - 1) != '\\'))
                break;
            else
                token.append(source.nextChar());
        }

        return new Token(TokenType.CONST_STRING, token.toString());
    }

    private Token processNumber(char currentChar) throws EmptySourceException {
        StringBuilder token = new StringBuilder();

        token.append(currentChar);

        while (Character.isDigit(source.peek()) && !source.isEoF()) {
            token.append(source.nextChar());
        }

        return new Token(TokenType.CONST_INT, token.toString());
    }

    private Token processKeywordOrIdentifier(char currentChar) throws EmptySourceException {
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

    private Token processLogicalOperator(char currentChar) throws EmptySourceException {
        StringBuilder token = new StringBuilder();

        token.append(currentChar);

        if (source.peek() == '&' && currentChar == '&' || source.peek() == '|' && currentChar == '|') {
            token.append(source.nextChar());
        } else
            return ErrorToken(source.peek());

        return KeywordsTable.get(token.toString());
    }

    private Token processRelationalOrAssignmentOperator(char currentChar) throws EmptySourceException {
        StringBuilder token = new StringBuilder();

        token.append(currentChar);

        if (source.peek() == '=') {
            token.append(source.nextChar());
        } else {
            if (currentChar == '!')
                return ErrorToken(source.peek());
        }

        return KeywordsTable.get(token.toString());
    }

    private Token processSingleCharacterToken(char currentChar) throws EmptySourceException {
        Token token = KeywordsTable.get(Character.toString(currentChar));

        if (token == null)
            return ErrorToken(currentChar);
        else
            return token;

    }
}