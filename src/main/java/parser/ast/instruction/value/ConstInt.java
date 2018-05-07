package parser.ast.instruction.value;

import parser.Scope;
import runtime.variable.IntegerVariable;
import runtime.variable.Variable;

// constInt = digit { digit }
public class ConstInt extends ConstValue {
    private int value;

    public ConstInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public Variable execute(Scope scope) {
        return new IntegerVariable(value);
    }
}
