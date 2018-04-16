package lexer;

import lexer.exception.EmptySourceException;
import lexer.token.Token;
import lexer.token.TokenType;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

// fixme: moglbym wszystkiemu dac sprawdzenie czy nie ma juz EOF
public class Scanner {
    private Source source;

    public Scanner() {
        source = new Source(System.in);
    }

    public Scanner(File fileSource) {
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
        else if (currentChar == '&' || currentChar == '|')
            token = processLogicalOperator(currentChar);
        else if (currentChar == '=' || currentChar == '!' || currentChar == '<' || currentChar == '>')
            token = processRelationalOperator(currentChar);
        else if (currentChar == '\"')
            token = processString(currentChar);
        else if (currentChar == '(' || currentChar == ')' || currentChar == '[' || currentChar == ']' || currentChar == '{' || currentChar == '}')
            token = KeywordsTable.get(Character.toString(currentChar));
        else
            token = processSingleCharacterToken(currentChar);

        if (token.getType() == TokenType.EMPTY) {
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

        while (source.peek() != '\"' && !source.isEoF())
            token.append(source.nextChar());

        token.append(source.nextChar());

        return new Token(TokenType.CONST_STRING, token.toString());
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
    
    private Token processRelationalOperator(char currentChar) {
        StringBuilder token = new StringBuilder();

        token.append(currentChar);

        if (source.peek() == '=') {
            token.append(source.nextChar());
        }

        return KeywordsTable.get(token.toString());
    }

    private Token processArithmeticOperator(char currentChar) {
        StringBuilder token = new StringBuilder();

        token.append(currentChar);

        return KeywordsTable.get(token.toString());
    }

    private Token processDate(StringBuilder token) {
        token.append(source.nextChar());
        
        try {
            tryParse(Character::isDigit, token, 2);
            tryParse(character -> character == '/', token, 1);
            tryParse(Character::isDigit, token, 4);
            if (source.peek() == ':') {
                token.append(source.nextChar());
                tryParse(Character::isDigit, token, 2);
                tryParse(character -> character == ':', token, 1);
                tryParse(Character::isDigit, token, 2);
                tryParse(character -> character == ':', token, 1);
                tryParse(Character::isDigit, token, 2);
            }
        } catch (Exception e) {
            return ErrorToken(source.peek());
        }

        return new Token(TokenType.CONST_DATE, token.toString());
    }

    private void tryParse(Predicate<Character> predicate, StringBuilder token, int length){
        for (int i = 0; i < length; ++i){
            if (predicate.test(source.peek()) && !source.isEoF())
                token.append(source.nextChar());
            else
                throw new NoSuchElementException();
        }
    }

    private Token processSingleCharacterToken(char currentChar) {
        Token token = KeywordsTable.get(Character.toString(currentChar));

        if (token == null)
            return ErrorToken(currentChar);
        else
            return token;
    }
}