package parser;

import lexer.Scanner;
import lexer.token.Token;
import lexer.token.TokenType;
import parser.ast.Node;
import parser.ast.instruction.Empty;
import parser.ast.instruction.Instruction;
import parser.ast.instruction.assignment.Assignment;
import parser.ast.instruction.call.Return;
import parser.ast.instruction.conditional.If;
import parser.ast.instruction.definition.function.FunctionDefinition;
import parser.ast.instruction.expression.Expression;
import parser.ast.instruction.loop.Foreach;

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
                accept(TokenType.IDENTIFIER);
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
        switch(current.getType()) {
            case COLON:
                return parseVariableDefinition();
            case ASSIGN_OP:
                return parseAssignment();
            case OPEN_BRACE:
                return parseFunctionCall();
                default:
                    throw new Exception(); // todo:
        }
    }

    private Instruction parseFunctionCall() throws Exception {

    }

    private Instruction parseVariableDefinition() throws Exception {

    }

    private Instruction parseAssignment() throws Exception {
        if (accept(TokenType.ASSIGN_OP)) {
            Assignment assignment = new Assignment();
            parseExpression();
        }
        else {
            // todo: throw exception? error handling?
        }
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
                TokenType.GREATER, TokenType.GREATER_EQUAL)){
            node = new Expression(node, temp.getType(), parseLowPriorityArithmeticExpression());

            temp = current;
        }

        return node;
    }

    private Node parseLowPriorityArithmeticExpression() throws Exception {
        Node node = parseHighPriorityArithmeticExpression();

        Token temp = current;
        while (accept(TokenType.PLUS, TokenType.MINUS)){
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
        }
        else
            return parseOperand();
    }

    private Node parseOperand() throws Exception {
        if (accept(TokenType.IDENTIFIER)){
            
        }
        else {
            return parseConstValue();
        }
    }

    private Node parseConstValue() throws Exception {

    }

    
}
