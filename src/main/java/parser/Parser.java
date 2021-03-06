package parser;

import lexer.Scanner;
import lexer.token.Token;
import lexer.token.TokenType;
import parser.ast.Identifier;
import parser.ast.Node;
import parser.ast.Type;
import parser.ast.instruction.Instruction;
import parser.ast.instruction.access.Access;
import parser.ast.instruction.access.ArrayAccess;
import parser.ast.instruction.assignment.Assignment;
import parser.ast.instruction.call.FunctionCall;
import parser.ast.instruction.call.Return;
import parser.ast.instruction.conditional.Else;
import parser.ast.instruction.conditional.If;
import parser.ast.instruction.definition.function.FunctionArgument;
import parser.ast.instruction.definition.function.FunctionDefinition;
import parser.ast.instruction.definition.variable.ArrayDefinition;
import parser.ast.instruction.definition.variable.FileAttribute;
import parser.ast.instruction.definition.variable.FileDefinition;
import parser.ast.instruction.definition.variable.PrimitiveDefinition;
import parser.ast.instruction.expression.Expression;
import parser.ast.instruction.loop.Foreach;
import parser.ast.instruction.value.ConstArray;
import parser.ast.instruction.value.ConstBool;
import parser.ast.instruction.value.ConstInt;
import parser.ast.instruction.value.ConstString;

import java.util.ArrayList;
import java.util.List;

import static lexer.token.TokenType.*;

public class Parser {
    private Scanner lexer;
    private Token current;

    public Parser(final Scanner lexer) {
        this.lexer = lexer;
        current = lexer.nextToken();
    }

    public Program parse() throws Exception {
        Program program = new Program();

        Instruction lastParsedInstruction;

        while (current.getType() != TokenType.EOF && (lastParsedInstruction = parseInstruction()) != null) {
            program.addInstruction(lastParsedInstruction);
        }

        return program;
    }

    private void accept(final TokenType... types) throws Exception {
        if (isAcceptable(types))
            current = lexer.nextToken();
        else {
            final String errorMessage = createErrorMessage(types);
            System.err.println(errorMessage);
            throw new Exception(errorMessage);
        }
    }

    private String createErrorMessage(final TokenType... types) {
        StringBuilder stringBuilder = new StringBuilder()
                .append("Error: ")
                .append("Unexpected token: ")
                .append(current.getValue())
                .append(" in line: ")
                .append(lexer.getLineNumber())
                .append(" at position: ")
                .append(lexer.getPositionInLine())
                .append(" Expected: ");

        for (final TokenType tokenType : types) {
            stringBuilder.append(tokenType);
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    private Instruction parseInstruction() {
        switch (current.getType()) {
            case DEF:
                return parseFunctionDefinition();
            case IF:
                return parseIf();
            case FOREACH:
                return parseForeach();
            case IDENTIFIER:
                return parseVariableDefinitionFunctionCallOrAssignment();
            case RETURN:
                return parseReturn();
            default:
                throw new RuntimeException(createErrorMessage(TokenType.DEF, TokenType.IF, TokenType.FOREACH, TokenType.IDENTIFIER, TokenType.RETURN));
        }
    }

    private Instruction parseFunctionDefinition()  {
        try {
            accept(TokenType.DEF);
            Identifier identifier = new Identifier(current.getValue());
            accept(TokenType.IDENTIFIER);
            List<FunctionArgument> arguments = parseFunctionArguments();
            accept(TokenType.COLON);
            Type returnType = parseType(current);
            accept(TokenType.INT_TYPE, TokenType.BOOL_TYPE, TokenType.STRING_TYPE, TokenType.DATE_TYPE, TokenType.FILE_TYPE, TokenType.CATALOGUE_TYPE);
            accept(TokenType.ASSIGN_OP);

            Program body = parseInstructionBlock();

            return new FunctionDefinition(identifier, arguments, body, returnType);
        } catch (Exception e) {
            return null;
        }
    }

    private Instruction parseIf(){
        try {
            accept(TokenType.IF);
            accept(TokenType.OPEN_BRACE);

            Node expression = parseExpression();

            accept(TokenType.CLOSED_BRACE);

            Program body = parseInstructionBlock();

            if (current.getType() == TokenType.ELSE) {
                accept(TokenType.ELSE);
                return new If(expression, body, new Else(parseInstructionBlock()));
            }

            return new If(expression, body, null);
        } catch (Exception e) {
            return null;
        }
    }

    private Instruction parseForeach() {
        try {
            accept(TokenType.FOREACH);
            Identifier iterator = new Identifier(current.getValue());

            accept(TokenType.IDENTIFIER);

            accept(COLON);

            Identifier collection = new Identifier(current.getValue());

            accept(TokenType.IDENTIFIER);

            Program body = parseInstructionBlock();

            return new Foreach(iterator, collection, body);
        } catch (Exception e) {
            return null;
        }
    }

    private Instruction parseReturn() {
        try {
            accept(TokenType.RETURN);
            return new Return(parseExpression());
        } catch (Exception e) {
            return null;
        }
    }

    private Instruction parseVariableDefinitionFunctionCallOrAssignment() {
        try {
            Identifier identifier = new Identifier(current.getValue());
            accept(TokenType.IDENTIFIER);
            switch (current.getType()) {
                case COLON:
                    return parseVariableDefinition(identifier);
                case ASSIGN_OP:
                    return parseAssignment(identifier);
                case OPEN_BRACE:
                    return parseFunctionCall(identifier);
                case PERIOD:
                    return parseAccess(identifier);
                case OPEN_SQUARE_BRACE:
                    return parseArrayAccess(identifier);
                default:
                    throw new RuntimeException(createErrorMessage(TokenType.COLON, TokenType.ASSIGN_OP, TokenType.OPEN_BRACE));
            }
        } catch (Exception e) {
            return null;
        }
    }

    private Instruction parseFunctionCall(Identifier identifier) throws Exception {
        return new FunctionCall(parseFunctionCallArguments(), identifier);
    }

    private Instruction parseVariableDefinition(Identifier identifier) throws Exception {
        accept(TokenType.COLON);

        switch (current.getType()) {
            case OPEN_SQUARE_BRACE:
                return parseArrayDefinition(identifier);
            case FILE_TYPE:
            case CATALOGUE_TYPE:
                return parseFileDefinition(identifier);
            case INT_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case DATE_TYPE:
                return parsePrimitiveDefinition(identifier);
            default:
                throw new RuntimeException(createErrorMessage(TokenType.OPEN_SQUARE_BRACE, TokenType.FILE_TYPE, TokenType.CATALOGUE_TYPE, TokenType.INT_TYPE, TokenType.STRING_TYPE, TokenType.BOOL_TYPE, TokenType.DATE_TYPE));
        }
    }

    // todo access
    private Instruction parseArrayDefinition(Identifier identifier) throws Exception {
        accept(TokenType.OPEN_SQUARE_BRACE);
        int count = 0;
        if (current.getType() == TokenType.CONST_INT) {
            count = Integer.parseInt(current.getValue());
            accept(TokenType.CONST_INT);
        }
        accept(TokenType.CLOSED_SQUARE_BRACE);

        Type type = parseType(current);

        accept(TokenType.INT_TYPE, TokenType.BOOL_TYPE, TokenType.STRING_TYPE, TokenType.DATE_TYPE, TokenType.FILE_TYPE, TokenType.CATALOGUE_TYPE);
        accept(TokenType.ASSIGN_OP);

        if (current.getType() == TokenType.OPEN_SQUARE_BRACE)
            return new ArrayDefinition(identifier.getName(), type, parseConstArray(), count);
        else
            return new ArrayDefinition(identifier.getName(), type, parseIdentifierOrFunctionCallOrAccess(), count);
    }

    private Instruction parseFileDefinition(Identifier identifier) throws Exception {
        Type type = parseType(current);
        accept(TokenType.FILE_TYPE, TokenType.CATALOGUE_TYPE);
        accept(TokenType.ASSIGN_OP);
        accept(TokenType.OPEN_CURLY_BRACE);

        ArrayList<FileAttribute> attributes = new ArrayList<>();

        while (isAcceptable(TokenType.IDENTIFIER)) {
            String name = current.getValue();
            accept(TokenType.IDENTIFIER);
            accept(TokenType.COLON);

            attributes.add(new FileAttribute(name, parseIdentifierOrConstValue()));
        }

        accept(TokenType.CLOSED_CURLY_BRACE);
        return new FileDefinition(identifier.getName(), type, attributes);
    }

    private Instruction parsePrimitiveDefinition(Identifier identifier) throws Exception {
        Type type = parseType(current);
        accept(TokenType.INT_TYPE, TokenType.STRING_TYPE, TokenType.BOOL_TYPE, TokenType.DATE_TYPE);

        return new PrimitiveDefinition(identifier.getName(), type, parseAssignment(identifier));
    }

    private Node parseIdentifierOrConstValue() throws Exception {
        Node value;

        if (current.getType() == TokenType.IDENTIFIER) {
            value = new Identifier(current.getValue());
            accept(TokenType.IDENTIFIER);
            return value;
        } else
            return parseConstValue();
    }

    private ConstArray parseConstArray() throws Exception {
        accept(TokenType.OPEN_SQUARE_BRACE);
        ArrayList<Node> values = new ArrayList<>();

        while (isAcceptable(TokenType.CONST_INT, TokenType.CONST_STRING, TokenType.CONST_BOOL)) {
            values.add(parseConstValue());

            if (current.getType() == COMMA)
                accept(COMMA);
            else if (current.getType() == TokenType.CLOSED_SQUARE_BRACE)
                break;
            else
                accept(COMMA, CLOSED_SQUARE_BRACE);
        }

        accept(TokenType.CLOSED_SQUARE_BRACE);
        return new ConstArray(values);
    }

    private Instruction parseAssignment(Identifier identifier) throws Exception {
        accept(TokenType.ASSIGN_OP);
        return new Assignment(parseExpression(), identifier);
    }

    private Node parseExpression() throws Exception {
        return parseLogicalExpression();
    }

    private Instruction parseAccess(Node from) throws Exception {
        accept(TokenType.PERIOD);
        Node identifier = parseIdentifierOrFunctionCallOrAccess();

        Instruction fromAccess = new Access(from, identifier);

        if (current.getType() == TokenType.PERIOD)
            return parseAccess(fromAccess);
        else
            return fromAccess;
    }

    private Instruction parseArrayAccess(Node from) throws Exception {
        accept(TokenType.OPEN_SQUARE_BRACE);

        Node index;

        if (isAcceptable(CONST_INT))
            index = parseConstValue();
        else
            index = parseIdentifierOrFunctionCallOrAccess();

        Instruction access = new ArrayAccess(from, index);

        accept(TokenType.CLOSED_SQUARE_BRACE);
        return access;
    }

    private Node parseLogicalExpression() throws Exception {
        Node node = parseRelationalExpression();

        while (isAcceptable(TokenType.AND, TokenType.OR)) {
            TokenType type = current.getType();
            accept(TokenType.AND, TokenType.OR);
            node = new Expression(node, type, parseRelationalExpression());
        }

        return node;
    }

    private Node parseRelationalExpression() throws Exception {
        Node node = parseLowPriorityArithmeticExpression();

        while (isAcceptable(
                TokenType.LESS, TokenType.LESS_EQUAL,
                TokenType.EQUAL, TokenType.NOT_EQUAL,
                TokenType.GREATER, TokenType.GREATER_EQUAL)) {

            TokenType type = current.getType();

            accept(TokenType.LESS, TokenType.LESS_EQUAL,
                    TokenType.EQUAL, TokenType.NOT_EQUAL,
                    TokenType.GREATER, TokenType.GREATER_EQUAL);

            node = new Expression(node, type, parseLowPriorityArithmeticExpression());
        }

        return node;
    }

    private Node parseLowPriorityArithmeticExpression() throws Exception {
        Node node = parseHighPriorityArithmeticExpression();

        while (isAcceptable(TokenType.PLUS, TokenType.MINUS)) {
            TokenType type = current.getType();
            accept(TokenType.PLUS, TokenType.MINUS);
            node = new Expression(node, type, parseHighPriorityArithmeticExpression());
        }

        return node;
    }

    private boolean isAcceptable(final TokenType... types) {
        for (final TokenType type : types) {
            if (type.equals(current.getType())) {
                return true;
            }
        }
        return false;
    }

    private Node parseHighPriorityArithmeticExpression() throws Exception {
        Node node = parseOperandOrExpression();

        while (isAcceptable(TokenType.MULTIPLY, TokenType.DIVIDE)) {
            TokenType type = current.getType();
            accept(TokenType.MULTIPLY, TokenType.DIVIDE);
            node = new Expression(node, type, parseOperandOrExpression());
        }
        return node;
    }

    private Node parseOperandOrExpression() throws Exception {
        if (current.getType() == TokenType.OPEN_BRACE) {
            accept(TokenType.OPEN_BRACE);
            Node node = parseExpression();
            accept(TokenType.CLOSED_BRACE);
            return node;
        } else
            return parseOperand();
    }

    private Node parseOperand() throws Exception {
        if (current.getType() == TokenType.IDENTIFIER)
            return parseIdentifierOrFunctionCallOrAccess();
        else
            return parseConstValue();
    }

    private Node parseIdentifierOrFunctionCallOrAccess() throws Exception {
        Identifier identifier = new Identifier(current.getValue());
        Node node = identifier;
        accept(TokenType.IDENTIFIER);

        if (current.getType() == TokenType.OPEN_BRACE)
            node = new FunctionCall(parseFunctionCallArguments(), identifier);
        else if (current.getType() == OPEN_SQUARE_BRACE)
            node = parseArrayAccess(identifier);

        if (current.getType() == TokenType.PERIOD)
            return parseAccess(node);
        else
            return node;
    }

    private List<FunctionArgument> parseFunctionArguments() throws Exception {
        ArrayList<FunctionArgument> arguments = new ArrayList<>();

        accept(TokenType.OPEN_BRACE);

        while (current.getType() != TokenType.EOF) {
            if (current.getType() == TokenType.IDENTIFIER) {
                String identifier = current.getValue();
                accept(TokenType.IDENTIFIER);
                accept(TokenType.COLON);
                Type type = parseType(current);
                accept(TokenType.INT_TYPE, TokenType.STRING_TYPE, TokenType.BOOL_TYPE, TokenType.FILE_TYPE, TokenType.CATALOGUE_TYPE);
                arguments.add(new FunctionArgument(type, identifier));
            }

            if (parseEndOfArgumentsOrComma()) return arguments;
        }
        return arguments;
    }

    private List<Node> parseFunctionCallArguments() throws Exception {
        ArrayList<Node> arguments = new ArrayList<>();

        accept(TokenType.OPEN_BRACE);

        while (current.getType() != TokenType.EOF) {
            Node arg;

            if (current.getType() == TokenType.CLOSED_BRACE) {
                accept(TokenType.CLOSED_BRACE);
                return arguments;
            } else
                arg = parseExpression();

            arguments.add(arg);

            if (parseEndOfArgumentsOrComma()) return arguments;
        }
        return arguments;
    }

    private boolean parseEndOfArgumentsOrComma() throws Exception {
        if (current.getType() == COMMA)
            accept(COMMA);
        else if (current.getType() == TokenType.CLOSED_BRACE) {
            accept(TokenType.CLOSED_BRACE);
            return true;
        } else
            accept(COMMA, TokenType.CLOSED_BRACE);
        return false;
    }

    private Node parseConstValue() throws Exception {
        switch (current.getType()) {
            case CONST_INT:
                String value = current.getValue();
                // todo: date
                accept(TokenType.CONST_INT);
                return new ConstInt(Integer.parseInt(value));
            case CONST_BOOL:
                ConstBool bool = new ConstBool((current.getValue().equals("true")));
                accept(TokenType.CONST_BOOL);
                return bool;
            case CONST_STRING:
                String string = current.getValue();
                accept(TokenType.CONST_STRING);
                return new ConstString(string);
            default:
                throw new RuntimeException(createErrorMessage(TokenType.CONST_INT, TokenType.CONST_BOOL, TokenType.CONST_STRING));
        }
    }

    private Type parseType(Token token) throws Exception {
        switch (token.getType()) {
            case INT_TYPE:
                return Type.INT;
            case STRING_TYPE:
                return Type.STRING;
            case BOOL_TYPE:
                return Type.BOOL;
            case DATE_TYPE:
                return Type.DATE;
            case FILE_TYPE:
                return Type.FILE;
            case CATALOGUE_TYPE:
                return Type.CATALOGUE;
            default:
                throw new RuntimeException(createErrorMessage(TokenType.INT_TYPE, TokenType.STRING_TYPE, TokenType.BOOL_TYPE, TokenType.DATE_TYPE, TokenType.FILE_TYPE, TokenType.CATALOGUE_TYPE));
        }
    }

    private Program parseInstructionBlock() throws Exception {
        Program body = new Program();

        accept(TokenType.OPEN_CURLY_BRACE);

        while (current.getType() != TokenType.CLOSED_CURLY_BRACE && current.getType() != TokenType.EOF)
            body.addInstruction(parseInstruction());

        accept(TokenType.CLOSED_CURLY_BRACE);

        return body;
    }
}
