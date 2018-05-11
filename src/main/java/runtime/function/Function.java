package runtime.function;

import parser.Program;
import parser.Scope;
import parser.ast.Type;
import parser.ast.instruction.call.FunctionCallArgument;
import parser.ast.instruction.definition.function.FunctionArgument;
import runtime.variable.Variable;
import runtime.variable.VoidVariable;

import java.util.List;

public class Function {
    private Program body;
    private Scope scope;
    private List<FunctionArgument> arguments;
    private Type returnType;

    public Function(Program body, Scope scope, List<FunctionArgument> arguments, Type returnType) {
        this.body = body;
        this.scope = scope;
        this.arguments = arguments;
        this.returnType = returnType;

        this.arguments.forEach(arg -> scope.addVariable(arg.getIdentifier(), VoidVariable.getInstance()));
    }

    public Variable call(List<FunctionCallArgument> callArguments) {
        if (callArguments.size() != arguments.size())
            throw new RuntimeException("Error: wrong number of arguments for function call");

        for (int i = 0; i < arguments.size(); ++i) {
            Type type = arguments.get(i).getType();
            FunctionCallArgument argument = callArguments.get(i);
            Variable callArgument = argument.execute(scope);

            if (type == callArgument.getType())
                scope.updateVariable(argument.getIdentifier(), callArgument);
            else
                throw new RuntimeException("Error: argument type mismatch. Expected: " + type + " found: " + callArgument.getType());
        }

        Variable result = body.executeInstructions(scope);

        if (result.getType() == returnType)
            return result;
        else
            throw new RuntimeException("Error: return type " + result.getType() + " does not match function's return type:  " + returnType);
    }
}