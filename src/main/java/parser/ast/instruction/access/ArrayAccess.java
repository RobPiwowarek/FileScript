package parser.ast.instruction.access;

import parser.Scope;
import parser.ast.Node;
import runtime.variable.Variable;

public class ArrayAccess extends Node {
    private Node from;
    private Node index;

    public ArrayAccess(Node from, Node index) {
        this.from = from;
        this.index = index;
    }

    @Override
    public Variable execute(Scope scope) {
        return null;
    }
}
