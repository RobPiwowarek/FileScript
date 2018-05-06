package parser.ast.instruction.conditional;

import parser.Program;

// ["else" instructionBlock]
public class Else {
    Program body;

    public Else(Program body) {
        this.body = body;
    }

    public Program getBody() {
        return body;
    }
}
