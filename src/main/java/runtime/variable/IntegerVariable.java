package runtime.variable;

public class IntegerVariable extends Variable {
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

    public IntegerVariable add(IntegerVariable variable) {
        return new IntegerVariable(variable.getValue() + value);
    }

    public IntegerVariable multiply(IntegerVariable variable) {
        return new IntegerVariable(variable.getValue() * value);
    }

    public IntegerVariable divide(IntegerVariable variable) {
        return new IntegerVariable(value / variable.getValue());
    }

    public IntegerVariable subtract(IntegerVariable variable) {
        return new IntegerVariable(value - variable.getValue());
    }
}
