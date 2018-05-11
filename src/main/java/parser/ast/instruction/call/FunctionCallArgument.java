package parser.ast.instruction.call;

import parser.Scope;
import parser.ast.Identifier;
import parser.ast.Node;
import runtime.variable.Variable;

public class FunctionCallArgument extends Node {
    private Identifier identifier;

    public FunctionCallArgument(Identifier identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier.getName();
    }

    @Override
    public Variable execute(Scope scope) {
        return identifier.execute(scope);
    }
}
