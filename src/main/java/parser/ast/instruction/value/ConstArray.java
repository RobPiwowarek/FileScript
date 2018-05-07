package parser.ast.instruction.value;

import parser.Scope;
import runtime.variable.Variable;
import runtime.variable.VoidVariable;

import java.util.List;

// constArray = '[' { constValue { ',' constValue } ']'
public class ConstArray extends ConstValue {
    private List<String> value;

    public ConstArray(List<String> value) {
        this.value = value;
    }

    public List<String> getValue() {
        return value;
    }

    @Override
    public Variable execute(Scope scope) {
        return VoidVariable.getInstance();
    }
}
