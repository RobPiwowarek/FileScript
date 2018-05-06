package parser.ast.instruction.value;

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
}
