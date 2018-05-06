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
import parser.ast.instruction.conditional.If;
import parser.ast.instruction.definition.function.FunctionArgument;
import parser.ast.instruction.definition.function.FunctionDefinition;
import parser.ast.instruction.expression.Expression;
import parser.ast.instruction.loop.Foreach;
import parser.ast.instruction.value.ConstBool;
import parser.ast.instruction.value.ConstDate;
import parser.ast.instruction.value.ConstInt;
import parser.ast.instruction.value.ConstString;

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
        return new FunctionDefinition();
    }

    private Instruction parseIf() throws Exception {
        return new If();
    }

    private Instruction parseForeach() throws Exception {
        return new Foreach();
    }

    private Instruction parseReturn() throws Exception {
        return new Return();
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
                    switch (current.getType()) {
                        case INT_TYPE:
                            argument = new FunctionArgument(Type.INT, identifier);
                            break;
                        case STRING_TYPE:
                            argument = new FunctionArgument(Type.STRING, identifier);
                            break;
                        case BOOL_TYPE:
                            argument = new FunctionArgument(Type.BOOL, identifier);
                            break;
                        case DATE_TYPE:
                            argument = new FunctionArgument(Type.DATE, identifier);
                            break;
                        case FILE_TYPE:
                            argument = new FunctionArgument(Type.FILE, identifier);
                            break;
                        case CATALOGUE_TYPE:
                            argument = new FunctionArgument(Type.CATALOGUE, identifier);
                            break;
                        default:
                            throw new RuntimeException("expected valid type"); // todo: empty node??
                    }
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
}
