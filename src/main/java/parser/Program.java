package parser;

import parser.ast.instruction.Instruction;

import java.util.ArrayList;
import java.util.List;

public class Program {
    private List<Instruction> body = new ArrayList<>();

    public void addInstruction(final Instruction instruction) {
        body.add(instruction);
    }
}
