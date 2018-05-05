package parser;

import lexer.Scanner;
import lexer.token.Token;
import lexer.token.TokenType;
import parser.ast.instruction.Instruction;
import parser.ast.instruction.call.Return;
import parser.ast.instruction.conditional.If;
import parser.ast.instruction.definition.function.FunctionDefinition;
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
                return new FunctionDefinition();
            case RETURN:
                return parseReturn();
            default:
                throw new Exception();
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
}
