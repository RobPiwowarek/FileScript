package parser.ast.instruction.call;

import parser.ast.Identifier;
import parser.ast.instruction.Instruction;
import parser.ast.instruction.definition.Definition;
import parser.ast.instruction.definition.function.FunctionArgument;

import java.util.List;

// functionCall = identifier '(' arguments ')'
public class FunctionCall extends Instruction {
    List<FunctionArgument> arguments;
    Identifier identifier;

    public FunctionCall(List<FunctionArgument> arguments, Identifier identifier) {
        this.arguments = arguments;
        this.identifier = identifier;
    }

    public List<FunctionArgument> getArguments() {
        return arguments;
    }

    public Identifier getIdentifier() {
        return identifier;
    }
}
