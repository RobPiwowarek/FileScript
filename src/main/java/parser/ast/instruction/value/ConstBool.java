package parser.ast.instruction.value;

import java.text.SimpleDateFormat;

// constBool = true | false
public class ConstBool extends ConstValue {
    private boolean value;

    public ConstBool(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }
}
