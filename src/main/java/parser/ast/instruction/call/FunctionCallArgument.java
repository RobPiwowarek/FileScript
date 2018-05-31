package parser.ast.instruction.call;

import parser.Scope;
import parser.ast.Identifier;
import parser.ast.Node;
import runtime.variable.Variable;

public class FunctionCallArgument extends Node {
    private Node argument;

    public FunctionCallArgument(Identifier identifier) {
        this.argument = identifier;
    }

    @Override
    public Variable execute(Scope scope) {
        return argument.execute(scope);
    }
}
