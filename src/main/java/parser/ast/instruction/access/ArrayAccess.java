package parser.ast.instruction.access;

import parser.Scope;
import parser.ast.Node;
import runtime.variable.ArrayVariable;
import runtime.variable.Variable;

public class ArrayAccess extends Access {
    private Node index;

    public ArrayAccess(Node index, Node from) {
        this.index = index;
        super.from = from;
    }

    @Override
    public Variable execute(Scope scope) {
        if (evaluatedOwner == null) {
            Variable result = from.execute(scope);
            return evaluate(scope, result);
        } else {
            return evaluate(scope, evaluatedOwner);
        }
    }

    private Variable evaluate(Scope scope, Variable evaluatedOwner) {
        if (evaluatedOwner instanceof ArrayVariable) {
            Variable result = ((ArrayVariable) evaluatedOwner).get(index);
            access.setOwner(result);
            return access.execute(scope);
        } else
            throw new RuntimeException("Error: cannot setOwner index of non-array variable");
    }

}