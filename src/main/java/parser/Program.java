package parser;

import parser.ast.Executable;
import parser.ast.instruction.Instruction;

import java.util.ArrayList;
import java.util.List;

public class Program {
    private List<Instruction> body = new ArrayList<>();

    public void addInstruction(final Instruction instruction) {
        body.add(instruction);
    }

    public void executeInstructions(Scope scope){
        body.stream()
                .map(instruction -> (Executable) instruction)
                .forEach(instruction -> instruction.execute(scope));
    }
}
