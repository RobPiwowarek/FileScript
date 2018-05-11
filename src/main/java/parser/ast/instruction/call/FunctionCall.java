package parser.ast.instruction.call;

import parser.Scope;
import parser.ast.Identifier;
import parser.ast.instruction.Instruction;
import runtime.variable.Variable;

import java.util.List;

// functionCall = identifier '(' arguments ')'
public class FunctionCall extends Instruction {
    private List<FunctionCallArgument> arguments;
    private Identifier identifier;

    public FunctionCall(List<FunctionCallArgument> arguments, Identifier identifier) {
        this.arguments = arguments;
        this.identifier = identifier;
    }

    @Override
    public Variable execute(Scope scope) {
        if (scope.containsFunction(identifier.getName())) {

            boolean argumentsExistInScope = arguments.stream()
                    .map(arg -> scope.containsVariable(arg.getIdentifier()))
                    .reduce(true, (a, b) -> a & b);

            if (!argumentsExistInScope)
                throw new RuntimeException("Error: could not find some of the arguments for function: " + identifier.getName());

            return scope.getFunction(identifier.getName()).call(arguments);
        } else
            throw new RuntimeException("Error: could not find function: " + identifier.getName());
    }

    public List<FunctionCallArgument> getArguments() {
        return arguments;
    }

    public Identifier getIdentifier() {
        return identifier;
    }
}
