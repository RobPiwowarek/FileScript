package lexer;

import lexer.token.PredefinedTokens;
import lexer.token.Token;
import lexer.token.TokenType;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScannerTest {

    @Test
    public void shouldParseIntType() {
        Scanner scanner = new Scanner("int");

        assertEquals(scanner.getNextToken(), KeywordsTable.get("int"));
    }

    @Test
    public void shouldParseBoolType() {
        Scanner scanner = new Scanner("bool");

        assertEquals(scanner.getNextToken(), KeywordsTable.get("bool"));
    }

    @Test
    public void shouldParseStringType() {
        Scanner scanner = new Scanner("string");

        assertEquals(scanner.getNextToken(), KeywordsTable.get("string"));
    }

    @Test
    public void shouldParseFileType() {
        Scanner scanner = new Scanner("file");

        assertEquals(scanner.getNextToken(), KeywordsTable.get("file"));
    }

    @Test
    public void shouldParseCatalogueType() {
        Scanner scanner = new Scanner("catalogue");

        assertEquals(scanner.getNextToken(), KeywordsTable.get("catalogue"));
    }

    @Test
    public void shouldParseDateType() {
        Scanner scanner = new Scanner("date");

        assertEquals(scanner.getNextToken(), KeywordsTable.get("date"));
    }

    @Test
    public void shouldParseAssignOp() {
        Scanner scanner = new Scanner("=");

        assertEquals(scanner.getNextToken(), KeywordsTable.get("="));
    }

    @Test
    public void shouldParseRelationalNotEqualOp() {
        Scanner scanner = new Scanner("!=");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Operators.Relational.NOT_EQUAL);
    }

    @Test
    public void shouldParseRelationalLessOp() {
        Scanner scanner = new Scanner("<");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Operators.Relational.LESS);
    }

    @Test
    public void shouldParseRelationalLessEqualOp() {
        Scanner scanner = new Scanner("<=");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Operators.Relational.LESS_EQUAL);
    }

    @Test
    public void shouldParseRelationalGreaterOp() {
        Scanner scanner = new Scanner(">");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Operators.Relational.GREATER);
    }

    @Test
    public void shouldParseRelationalGreaterEqualOp() {
        Scanner scanner = new Scanner(">=");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Operators.Relational.GREATER_EQUAL);
    }

    @Test
    public void shouldParseArithmeticPlusOp() {
        Scanner scanner = new Scanner("+");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Operators.Arithmetic.PLUS);
    }

    @Test
    public void shouldParseArithmeticMinusOp() {
        Scanner scanner = new Scanner("-");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Operators.Arithmetic.MINUS);
    }

    @Test
    public void shouldParseArithmeticMultiplyOp() {
        Scanner scanner = new Scanner("*");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Operators.Arithmetic.MULTIPLY);
    }

    @Test
    public void shouldParseArithmeticDivideOp() {
        Scanner scanner = new Scanner("/");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Operators.Arithmetic.DIVIDE);
    }

    @Test
    public void shouldParseLogicalAndOp() {
        Scanner scanner = new Scanner("&&");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Operators.Logical.AND);
    }

    @Test
    public void shouldParseLogicalOrOp() {
        Scanner scanner = new Scanner("||");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Operators.Logical.OR);
    }


    @Test
    public void shouldParseIfKeyword() {
        Scanner scanner = new Scanner("if");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Others.IF);
    }

    @Test
    public void shouldParseElseKeyword() {
        Scanner scanner = new Scanner("else");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Others.ELSE);
    }

    @Test
    public void shouldParseForeachKeyword() {
        Scanner scanner = new Scanner("foreach");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Others.FOREACH);
    }

    @Test
    public void shouldParseConstInt() {
        String value = "12345";
        Scanner scanner = new Scanner(value);

        Token token = scanner.getNextToken();

        assertEquals(token.getType(), TokenType.CONST_INT);
        assertEquals(token.getValue(), value);
    }

    @Test
    public void shouldParseConstString() {
        String value = "\"12345\"";
        Scanner scanner = new Scanner(value);

        Token token = scanner.getNextToken();

        assertEquals(token.getType(), TokenType.CONST_STRING);
        assertEquals(token.getValue(), value);
    }

    @Test
    public void shouldParseConstStringWithEscapedQuotesInside() {
        String value = "\"\\\"12345\\\"\"";
        Scanner scanner = new Scanner(value);

        Token token = scanner.getNextToken();

        assertEquals(token.getType(), TokenType.CONST_STRING);
        assertEquals(token.getValue(), value);
    }

    @Test
    public void shouldParseConstTrueBool() {
        Scanner scanner = new Scanner("true");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Constants.TRUE);
    }

    @Test
    public void shouldParseConstFalseBool() {
        Scanner scanner = new Scanner("false");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Constants.FALSE);
    }

    @Test
    public void shouldParseSimpleIdentifier() {
        String value = "abcd";
        Scanner scanner = new Scanner(value);

        Token token = scanner.getNextToken();

        assertEquals(token.getType(), TokenType.IDENTIFIER);
        assertEquals(token.getValue(), value);
    }

    @Test
    public void shouldParseComma() {
        Scanner scanner = new Scanner(",");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Others.COMMA);
    }


    @Test
    public void shouldParseColon() {
        Scanner scanner = new Scanner(":");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Others.COLON);
    }


    @Test
    public void shouldParseSemiColon() {
        Scanner scanner = new Scanner(";");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Others.SEMICOLON);
    }


    @Test
    public void shouldParsePeriod() {
        Scanner scanner = new Scanner(".");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Others.PERIOD);
    }

    @Test
    public void shouldParseReturnKeyword() {
        Scanner scanner = new Scanner("return");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Others.RETURN);
    }

    @Test
    public void shouldParseOpenBracer() {
        Scanner scanner = new Scanner("(");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Bracers.OPEN_BRACE);
    }

    @Test
    public void shouldParseClosedBracer() {
        Scanner scanner = new Scanner(")");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Bracers.CLOSED_BRACE);
    }

    @Test
    public void shouldParseOpenSquareBracer() {
        Scanner scanner = new Scanner("[");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Bracers.OPEN_SQUARE_BRACE);
    }

    @Test
    public void shouldParseClosedSquareBracer() {
        Scanner scanner = new Scanner("]");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Bracers.CLOSED_SQUARE_BRACE);
    }

    @Test
    public void shouldParseOpenCurlyBracer() {
        Scanner scanner = new Scanner("{");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Bracers.OPEN_CURLY_BRACE);
    }

    @Test
    public void shouldParseClosedCurlyBracer() {
        Scanner scanner = new Scanner("}");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Bracers.CLOSED_CURLY_BRACE);
    }

    @Test
    public void shouldParseDefKeyword() {
        Scanner scanner = new Scanner("def");

        assertEquals(scanner.getNextToken(), PredefinedTokens.Others.DEF);
    }

    @Test
    public void shouldCorrectlyIgnoreWhitespaces() {
        Scanner scanner = new Scanner("       andvsf          kdaslkjadj");

        Token token = scanner.getNextToken();
        Token token2 = scanner.getNextToken();

        assertEquals(token.getValue(), "andvsf");
        assertEquals(token.getType(), TokenType.IDENTIFIER);

        assertEquals(token2.getValue(), "kdaslkjadj");
        assertEquals(token2.getType(), TokenType.IDENTIFIER);
    }

    @Test
    public void shouldCorrectlyParseAllTokens() {
        String tokens = "funkcja(); abcd: int = 5; def funkcja() = { println(abcd); }";

        Scanner scanner = new Scanner(tokens);

        Token token = scanner.getNextToken();
        assertEquals(token.getValue(), "funkcja");
        assertEquals(token.getType(), TokenType.IDENTIFIER);

        token = scanner.getNextToken();
        assertEquals(token, PredefinedTokens.Bracers.OPEN_BRACE);

        token = scanner.getNextToken();
        assertEquals(token, PredefinedTokens.Bracers.CLOSED_BRACE);

        token = scanner.getNextToken();
        assertEquals(token, PredefinedTokens.Others.SEMICOLON);

        token = scanner.getNextToken();
        assertEquals(token.getType(), TokenType.IDENTIFIER);
        assertEquals(token.getValue(), "abcd");

        token = scanner.getNextToken();
        assertEquals(token, PredefinedTokens.Others.COLON);

        token = scanner.getNextToken();
        assertEquals(token, PredefinedTokens.Types.INT);

        token = scanner.getNextToken();
        assertEquals(token, PredefinedTokens.Operators.ASSIGN);

        token = scanner.getNextToken();
        assertEquals(token.getValue(), "5");
        assertEquals(token.getType(), TokenType.CONST_INT);

        token = scanner.getNextToken();
        assertEquals(token, PredefinedTokens.Others.SEMICOLON);

        token = scanner.getNextToken();
        assertEquals(token, PredefinedTokens.Others.DEF);

        token = scanner.getNextToken();
        assertEquals(token.getValue(), "funkcja");
        assertEquals(token.getType(), TokenType.IDENTIFIER);

        token = scanner.getNextToken();
        assertEquals(token, PredefinedTokens.Bracers.OPEN_BRACE);

        token = scanner.getNextToken();
        assertEquals(token, PredefinedTokens.Bracers.CLOSED_BRACE);

        token = scanner.getNextToken();
        assertEquals(token, PredefinedTokens.Operators.ASSIGN);

        token = scanner.getNextToken();
        assertEquals(token, PredefinedTokens.Bracers.OPEN_CURLY_BRACE);

        token = scanner.getNextToken();
        assertEquals(token.getValue(), "println");
        assertEquals(token.getType(), TokenType.IDENTIFIER);

        token = scanner.getNextToken();
        assertEquals(token, PredefinedTokens.Bracers.OPEN_BRACE);

        token = scanner.getNextToken();
        assertEquals(token.getValue(), "abcd");
        assertEquals(token.getType(), TokenType.IDENTIFIER);

        token = scanner.getNextToken();
        assertEquals(token, PredefinedTokens.Bracers.CLOSED_BRACE);

        token = scanner.getNextToken();
        assertEquals(token, PredefinedTokens.Others.SEMICOLON);

        token = scanner.getNextToken();
        assertEquals(token, PredefinedTokens.Bracers.CLOSED_CURLY_BRACE);
    }
}