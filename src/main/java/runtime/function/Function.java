package runtime.function;

import parser.Program;
import parser.Scope;
import parser.ast.Identifier;
import parser.ast.Type;
import runtime.variable.Variable;

import java.util.Map;

public class Function {
    private Program body;
    private Scope scope;
    private Map<Identifier, Variable> arguments;
    private Type returnType;

    public Function(Program body, Scope scope, Map<Identifier, Variable> arguments, Type returnType) {
        this.body = body;
        this.scope = scope;
        this.arguments = arguments;
        this.returnType = returnType;
    }
}
