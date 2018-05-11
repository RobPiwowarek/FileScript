package parser;

import parser.ast.instruction.Instruction;
import parser.ast.instruction.call.Return;
import runtime.variable.Variable;
import runtime.variable.VoidVariable;

import java.util.ArrayList;
import java.util.List;

public class Program {
    private List<Instruction> body = new ArrayList<>();

    public void addInstruction(final Instruction instruction) {
        body.add(instruction);
    }

    public Variable executeInstructions(Scope scope) {
        for (Instruction instruction : body) {
            Variable result = instruction.execute(scope);

            if (instruction instanceof Return)
                return result;
        }

        return VoidVariable.getInstance();
    }
}
