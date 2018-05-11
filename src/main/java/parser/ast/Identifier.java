package parser.ast;

import parser.Scope;
import runtime.variable.Variable;

public class Identifier extends Node {
    private String name;

    public Identifier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Variable execute(Scope scope) {
        return scope.getVariable(name);
    }
}
