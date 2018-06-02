package parser.ast.instruction.access;

import parser.Scope;
import parser.ast.Node;
import parser.ast.instruction.Instruction;
import runtime.variable.ArrayVariable;
import runtime.variable.IntegerVariable;
import runtime.variable.Variable;

public class ArrayAccess extends Instruction {
    private Node from;
    private Node index;

    public ArrayAccess(Node from, Node index) {
        this.from = from;
        this.index = index;
    }

    public Node getIndex() {
        return index;
    }

    public Node getFrom() {
        return from;
    }

    @Override
    public Variable execute(Scope scope) {
        IntegerVariable evaluatedIndex = (IntegerVariable) index.execute(scope);
        ArrayVariable evaluatedFrom = (ArrayVariable) from.execute(scope);

        return evaluatedFrom.get(evaluatedIndex.getValue());
    }
}
