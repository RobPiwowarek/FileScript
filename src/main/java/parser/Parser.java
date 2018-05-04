package parser;

import lexer.Scanner;
import lexer.token.Token;
import lexer.token.TokenType;
import parser.ast.Node;
import parser.ast.instruction.Instruction;

public class Parser {
    private Scanner lexer;

    public Parser(final Scanner lexer) {
        this.lexer = lexer;
    }

    public Program parse() throws Exception {
        Program program = new Program();

        Instruction lastParsedInstruction;

        while ((lastParsedInstruction = parseInstruction()) != null){
            program.addInstruction(lastParsedInstruction);
        }

        return program;
    }

    private Token accept(final TokenType... types) throws Exception {

    }

    private Instruction parseInstruction() throws Exception {

    }

}
