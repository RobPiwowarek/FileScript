package runtime.variable;

import parser.ast.Type;
import runtime.Comparable;

public class IntegerVariable extends Variable implements Comparable {
    private Integer value;

    public IntegerVariable(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    Type getType() {
        return Type.INT;
    }

    @Override
    public int compare(Object object) {
        if (object instanceof IntegerVariable) {
            IntegerVariable integerVariable = (IntegerVariable) object;

            if (value > integerVariable.getValue())
                return 1;
            else if (value < integerVariable.getValue())
                return -1;
            else
                return 0;
        }
        else
            throw new RuntimeException("Error. Comparing IntegerVariable with a non-IntegerVariable type");
    }
}
