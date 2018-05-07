package parser.ast.instruction.call;

import parser.Scope;
import parser.ast.Identifier;
import parser.ast.instruction.Instruction;
import parser.ast.instruction.definition.function.FunctionArgument;
import runtime.variable.Variable;
import runtime.variable.VoidVariable;

import java.util.List;

// functionCall = identifier '(' arguments ')'
public class FunctionCall extends Instruction {
    List<FunctionArgument> arguments;
    Identifier identifier;

    public FunctionCall(List<FunctionArgument> arguments, Identifier identifier) {
        this.arguments = arguments;
        this.identifier = identifier;
    }

    @Override
    public Variable execute(Scope scope) {
        return VoidVariable.getInstance(); // todo runtime'owa reprezentacja funkcji
    }

    public List<FunctionArgument> getArguments() {
        return arguments;
    }

    public Identifier getIdentifier() {
        return identifier;
    }
}
