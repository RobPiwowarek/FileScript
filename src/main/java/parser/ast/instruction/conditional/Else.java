package parser.ast.instruction.conditional;

import parser.Program;
import parser.ast.instruction.Instruction;
import parser.ast.instruction.definition.Definition;

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
