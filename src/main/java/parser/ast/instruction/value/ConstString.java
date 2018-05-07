package parser.ast.instruction.value;

import parser.Scope;
import runtime.variable.StringVariable;
import runtime.variable.Variable;

// constString = '"' { alpha | digit } '"'
public class ConstString extends ConstValue {
    private String value;

    public ConstString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Variable execute(Scope scope) {
        return new StringVariable(value);
    }
}
