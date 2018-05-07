package parser.ast;

import parser.Scope;
import runtime.variable.Variable;

public abstract class Node {
    public abstract Variable execute(Scope scope);
}
