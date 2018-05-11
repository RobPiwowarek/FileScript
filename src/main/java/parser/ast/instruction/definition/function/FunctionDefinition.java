package parser.ast.instruction.definition.function;

import parser.Program;
import parser.Scope;
import parser.ast.Identifier;
import parser.ast.Type;
import parser.ast.instruction.definition.Definition;
import runtime.function.Function;
import runtime.variable.Variable;
import runtime.variable.VoidVariable;

import java.util.List;

// functionDefinition = "def" identifier '(' argumentsWithTypes ')' assignmentOperator instructionBlock
public class FunctionDefinition extends Definition {
    private Identifier identifier;
    private List<FunctionArgument> arguments;
    private Program body;
    private Type returnType;

    public FunctionDefinition(Identifier identifier, List<FunctionArgument> arguments, Program body, Type returnType) {
        this.identifier = identifier;
        this.arguments = arguments;
        this.body = body;
        this.returnType = returnType;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public List<FunctionArgument> getArguments() {
        return arguments;
    }

    public Program getBody() {
        return body;
    }

    @Override
    public Variable execute(Scope scope) {
        Scope subScope = new Scope(scope);

        scope.addFunction(identifier.getName(), new Function(body, subScope, arguments, returnType));
        return VoidVariable.getInstance();
    }
}
