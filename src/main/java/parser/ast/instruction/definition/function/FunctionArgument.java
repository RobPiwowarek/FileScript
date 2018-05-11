package parser.ast.instruction.definition.function;

import parser.Scope;
import parser.ast.Node;
import parser.ast.Type;
import runtime.variable.Variable;
import runtime.variable.VoidVariable;

// argumentsWithTypes = { identifier ':' type [ ',' identifier ':' type ] }
public class FunctionArgument extends Node {
    private Type type;
    private String identifier;

    public FunctionArgument(Type type, String identifier) {
        this.type = type;
        this.identifier = identifier;
    }

    public Type getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public Variable execute(Scope scope) {
        return VoidVariable.getInstance();
    }
}
