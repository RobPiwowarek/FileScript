package runtime.variable;

import parser.ast.Type;
import runtime.Comparable;

public class BoolVariable extends Variable implements Comparable {
    private Boolean value;

    public BoolVariable(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    Type getType() {
        return Type.BOOL;
    }

    @Override
    public int compare(Object object) {
        if (object instanceof BoolVariable) {
            BoolVariable bool = (BoolVariable) object;
            if (value && value != bool.getValue())
                return -1;
            else if (!value && value != bool.getValue())
                return 1;
            else
                return 0;
        } else
            throw new RuntimeException("Error. Comparing BoolVariable with a non-BoolVariable type");

    }
}
