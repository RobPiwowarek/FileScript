package parser.ast.instruction.value;

import parser.Scope;
import runtime.variable.BoolVariable;
import runtime.variable.Variable;

// constBool = true | false
public class ConstBool extends ConstValue {
    private boolean value;

    public ConstBool(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }

    @Override
    public Variable execute(Scope scope) {
        return new BoolVariable(value);
    }
}
