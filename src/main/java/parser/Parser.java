package parser;

import lexer.Scanner;
import lexer.token.Token;
import lexer.token.TokenType;
import parser.ast.Identifier;
import parser.ast.Node;
import parser.ast.Type;
import parser.ast.instruction.Instruction;
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
import parser.ast.instruction.value.*;

import java.util.ArrayList;
import java.util.List;

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

        while ((lastParsedInstruction = parseInstruction()) != null) {
            program.addInstruction(lastParsedInstruction);
        }

        return program;
    }

    private boolean accept(final TokenType... types) throws Exception {
        for (final TokenType type : types) {
            if (type.equals(current.getType())) {
                current = lexer.nextToken();
                return true;
            }
        }
        return false;
    }

    private Instruction parseInstruction() throws Exception {
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
                throw new Exception(); // todo:
        }
    }

    private Instruction parseFunctionDefinition() throws Exception {
        accept(TokenType.DEF);
        Identifier identifier = new Identifier(current.getValue());
        accept(TokenType.IDENTIFIER);
        accept(TokenType.OPEN_BRACE);
        List<FunctionArgument> arguments = parseFunctionArguments();
        accept(TokenType.ASSIGN_OP);
        accept(TokenType.OPEN_CURLY_BRACE);

        Program body = new Program();

        while (current.getType() != TokenType.CLOSED_CURLY_BRACE && current.getType() != TokenType.EOF)
            body.addInstruction(parseInstruction());

        accept(TokenType.CLOSED_CURLY_BRACE);

        return new FunctionDefinition(identifier, arguments, body);
    }

    private Instruction parseIf() throws Exception {
        accept(TokenType.IF);
        accept(TokenType.OPEN_BRACE);

        Node expression = parseExpression();

        accept(TokenType.CLOSED_BRACE);

        Program body = new Program();

        accept(TokenType.OPEN_CURLY_BRACE); // todo: make a separate function out of it
        while (current.getType() != TokenType.CLOSED_CURLY_BRACE && current.getType() != TokenType.EOF)
            body.addInstruction(parseInstruction());

        accept(TokenType.CLOSED_CURLY_BRACE);

        Else elseBlock = null;

        if (current.getType() == TokenType.ELSE) {
            accept(TokenType.ELSE);

            Program elseBody = new Program();

            accept(TokenType.OPEN_CURLY_BRACE); // todo: make a separate function out of it
            while (current.getType() != TokenType.CLOSED_CURLY_BRACE && current.getType() != TokenType.EOF)
                elseBody.addInstruction(parseInstruction());

            elseBlock = new Else(elseBody);
        }

        return new If(expression, body, elseBlock);
    }

    private Instruction parseForeach() throws Exception {
        accept(TokenType.FOREACH);

        Identifier iterator = new Identifier(current.getValue());

        accept(TokenType.IDENTIFIER);

        Identifier collection = new Identifier(current.getValue());

        accept(TokenType.IDENTIFIER);

        Program body = new Program();

        accept(TokenType.OPEN_CURLY_BRACE); // todo: make a separate function out of it
        while (current.getType() != TokenType.CLOSED_CURLY_BRACE && current.getType() != TokenType.EOF)
            body.addInstruction(parseInstruction());

        return new Foreach(iterator, collection, body);
    }

    private Instruction parseReturn() throws Exception {
        accept(TokenType.RETURN);
        return new Return(parseExpression());
    }

    private Instruction parseVariableDefinitionFunctionCallOrAssignment() throws Exception {
        Identifier identifier = new Identifier(current.getValue());
        accept(TokenType.IDENTIFIER);

        switch (current.getType()) {
            case COLON:
                return parseVariableDefinition(identifier);
            case ASSIGN_OP:
                return parseAssignment(identifier);
            case OPEN_BRACE:
                return parseFunctionCall(identifier);
            default:
                throw new Exception(); // todo:
        }
    }

    private Instruction parseFunctionCall(Identifier identifier) throws Exception {
        return new FunctionCall(parseFunctionArguments(), identifier);
    }

    private Instruction parseVariableDefinition(Identifier identifier) throws Exception {
        accept(TokenType.COLON);

        Type type = null;

        switch (current.getType()) {
            case OPEN_SQUARE_BRACE:
                accept(TokenType.OPEN_SQUARE_BRACE);
                int count = 0;
                if (current.getType() == TokenType.CONST_INT) {
                    count = Integer.parseInt(current.getValue());
                    accept(TokenType.CONST_INT);
                }
                accept(TokenType.CLOSED_SQUARE_BRACE);

                type = parseType(current);

                accept(TokenType.INT_TYPE, TokenType.BOOL_TYPE, TokenType.STRING_TYPE, TokenType.DATE_TYPE, TokenType.FILE_TYPE, TokenType.CATALOGUE_TYPE);

                accept(TokenType.ASSIGN_OP);

                Node node;

                if (current.getType() == TokenType.OPEN_SQUARE_BRACE)
                    node = parseConstArray();
                else
                    node = parseIdentifierOrFunctionCall();

                return new ArrayDefinition(identifier.getName(), type, node, count);
            case FILE_TYPE:
                type = Type.FILE;
            case CATALOGUE_TYPE:
                if (type == null)
                    type = Type.CATALOGUE;

                accept(TokenType.FILE_TYPE, TokenType.CATALOGUE_TYPE);
                accept(TokenType.ASSIGN_OP);
                accept(TokenType.OPEN_CURLY_BRACE);

                ArrayList<FileAttribute> attributes = new ArrayList<>();

                Token temp = current;
                while (accept(TokenType.IDENTIFIER)) {
                    Node value;
                    accept(TokenType.COLON);

                    if (current.getType() == TokenType.IDENTIFIER) {
                        value = new Identifier(current.getValue());
                        accept(TokenType.IDENTIFIER);
                    } else {
                        value = parseConstValue();
                    }

                    attributes.add(new FileAttribute(temp.getValue(), value));

                    temp = current;
                }

                accept(TokenType.CLOSED_CURLY_BRACE);
                return new FileDefinition(identifier.getName(), type, attributes);
            case INT_TYPE:
                type = Type.INT;
            case STRING_TYPE:
                if (type == null) type = Type.STRING;
            case BOOL_TYPE:
                if (type == null) type = Type.BOOL;
            case DATE_TYPE:
                if (type == null) type = Type.DATE;
                accept(TokenType.INT_TYPE, TokenType.STRING_TYPE, TokenType.BOOL_TYPE, TokenType.DATE_TYPE);
                accept(TokenType.ASSIGN_OP);

                Node val;

                if (current.getType() == TokenType.IDENTIFIER) {
                    val = new Identifier(current.getValue());
                    accept(TokenType.IDENTIFIER);
                } else {
                    val = parseConstValue();
                }
                return new PrimitiveDefinition(identifier.getName(), type, val);
            default:
                throw new RuntimeException("expected [ or valid type"); // todo:
        }
    }

    private ConstArray parseConstArray() throws Exception {
        accept(TokenType.OPEN_SQUARE_BRACE);
        ArrayList<String> values = new ArrayList<>();

        Token temp = current;
        while (accept(TokenType.CONST_INT, TokenType.CONST_STRING, TokenType.CONST_BOOL)) {
            values.add(temp.getValue());

            accept(TokenType.COMMA);

            temp = current;
        }

        accept(TokenType.CLOSED_SQUARE_BRACE);

        return new ConstArray(values);
    }

    private Instruction parseAssignment(Identifier identifier) throws Exception {
        if (accept(TokenType.ASSIGN_OP)) {
            return new Assignment(parseExpression(), identifier);
        } else
            throw new RuntimeException("expected ="); // todo:
    }

    private Node parseExpression() throws Exception {
        return parseLogicalExpression();
    }

    private Node parseLogicalExpression() throws Exception {
        Node node = parseRelationalExpression();

        Token temp = current;
        while (accept(TokenType.AND, TokenType.OR)) {
            node = new Expression(node, temp.getType(), parseRelationalExpression());

            temp = current;
        }

        return node;
    }

    private Node parseRelationalExpression() throws Exception {
        Node node = parseLowPriorityArithmeticExpression();

        Token temp = current;
        while (accept(
                TokenType.LESS, TokenType.LESS_EQUAL,
                TokenType.EQUAL, TokenType.NOT_EQUAL,
                TokenType.GREATER, TokenType.GREATER_EQUAL)) {
            node = new Expression(node, temp.getType(), parseLowPriorityArithmeticExpression());

            temp = current;
        }

        return node;
    }

    private Node parseLowPriorityArithmeticExpression() throws Exception {
        Node node = parseHighPriorityArithmeticExpression();

        Token temp = current;
        while (accept(TokenType.PLUS, TokenType.MINUS)) {
            node = new Expression(node, temp.getType(), parseHighPriorityArithmeticExpression());

            temp = current;
        }

        return node;
    }

    private Node parseHighPriorityArithmeticExpression() throws Exception {
        if (accept(TokenType.OPEN_BRACE)) {
            Node node = parseExpression();
            accept(TokenType.CLOSED_BRACE);
            return node;
        } else
            return parseOperand();
    }

    private Node parseOperand() throws Exception {
        if (current.getType() == TokenType.IDENTIFIER)
            return parseIdentifierOrFunctionCall();
        else
            return parseConstValue();
    }

    private Node parseIdentifierOrFunctionCall() throws Exception {
        Token temp = current;

        if (accept(TokenType.IDENTIFIER)) {
            Identifier identifier = new Identifier(temp.getValue());

            if (current.getType() == TokenType.OPEN_BRACE) {
                return new FunctionCall(parseFunctionArguments(), identifier);
            } else {
                return identifier;
            }
        } else {
            throw new RuntimeException("Identifier expected"); // todo:
        }
    }

    private List<FunctionArgument> parseFunctionArguments() throws Exception {
        ArrayList<FunctionArgument> arguments = new ArrayList<>();

        if (accept(TokenType.OPEN_BRACE)) {
            Token temp = current;

            while (accept(TokenType.IDENTIFIER)) {
                Identifier identifier = new Identifier(temp.getValue());
                FunctionArgument argument = null; // fixme

                if (accept(TokenType.COLON)) {
                    argument = new FunctionArgument(parseType(current), identifier);
                }

                accept(TokenType.COMMA); // todo:
                arguments.add(argument);
                temp = current;
            }
        }

        if (!accept(TokenType.CLOSED_BRACE))
            throw new RuntimeException("expected )"); // todo:

        return arguments;
    }

    private Node parseConstValue() throws Exception {
        switch (current.getType()) {
            case CONST_INT:
                String value = current.getValue();

                // todo: errors
                if (current.getType() == TokenType.DIVIDE) {
                    StringBuilder builder = new StringBuilder(value);
                    builder.append('/');
                    accept(TokenType.DIVIDE);
                    builder.append(current.getValue());
                    builder.append('/');
                    accept(TokenType.DIVIDE);
                    builder.append(current.getValue());
                    // todo: parse time
                    return new ConstDate();
                } else
                    return new ConstInt(Integer.parseInt(value));
            case CONST_BOOL:
                ConstBool bool = new ConstBool((current.getValue().equals("true"))); // todo: error detection
                accept(TokenType.CONST_BOOL);
                return bool;
            case CONST_STRING:
                String string = current.getValue();
                accept(TokenType.CONST_STRING);
                return new ConstString(string);
            default:
                throw new RuntimeException("expected const value"); // todo:
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
                throw new RuntimeException("expected valid type"); // todo: empty node??
        }
    }
}
