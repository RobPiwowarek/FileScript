package runtime.function;

import parser.Program;
import parser.Scope;
import parser.ast.Identifier;
import parser.ast.Node;
import parser.ast.Type;
import parser.ast.instruction.call.FunctionCall;
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
        this.scope = scope.copy();
        this.arguments = arguments;
        this.returnType = returnType;

        this.arguments.forEach(arg -> scope.addVariable(arg.getIdentifier(), VoidVariable.getInstance()));
    }

    private void argumentsExistInScope(List<Node> callArguments) {
        boolean argumentsExistInScope = callArguments
                .stream()
                .filter(arg -> arg instanceof Identifier)
                .map(arg -> scope.containsVariable(((Identifier) arg).getName()))
                .reduce(true, (a, b) -> a && b);

        boolean functionsExistInScope = callArguments
                .stream()
                .filter(arg -> arg instanceof FunctionCall)
                .map(arg -> ((FunctionCall) arg).getIdentifier().getName())
                .map(name -> scope.containsFunction(name))
                .reduce(true, (a, b) -> a && b);

        argumentsExistInScope = argumentsExistInScope && functionsExistInScope;

        if (!argumentsExistInScope)
            throw new RuntimeException("Error: could not find some of the arguments for function");
    }

    public Variable call(List<Node> callArguments) {
        if (callArguments.size() != arguments.size())
            throw new RuntimeException("Error: wrong number of arguments for function call");

        for (int i = 0; i < arguments.size(); ++i) {
            FunctionArgument arg = arguments.get(i);
            Type type = arg.getType();
            argumentsExistInScope(callArguments);
            Variable evaluatedArgument = callArguments.get(i).execute(scope);

            if (type == evaluatedArgument.getType())
                scope.updateVariable(arg.getIdentifier(), evaluatedArgument);
            else
                throw new RuntimeException("Error: argument type mismatch. Expected: " + type + " found: " + evaluatedArgument.getType());
        }

        Variable result = body.executeInstructions(scope);

        if (result.getType() == returnType)
            return result;
        else
            throw new RuntimeException("Error: return type " + result.getType() + " does not match function's return type:  " + returnType);
    }
}