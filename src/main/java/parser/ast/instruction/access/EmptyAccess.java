package parser.ast.instruction.access;

import parser.Scope;
import parser.ast.Node;
import runtime.variable.ArrayVariable;
import runtime.variable.Variable;
import runtime.variable.VoidVariable;

public class EmptyAccess extends Access {
    @Override
    public Variable execute(Scope scope) {
        return VoidVariable.getInstance();
    }
}