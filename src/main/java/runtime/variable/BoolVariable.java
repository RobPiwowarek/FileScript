package runtime.variable;

public class BoolVariable extends Variable {
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

    public Boolean and(Boolean bool) {
        return value && bool;
    }

    public Boolean or(Boolean bool) {
        return value || bool;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
