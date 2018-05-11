package runtime.variable;

import parser.ast.Type;

public class StringVariable extends Variable {
    private StringBuilder value;

    public StringVariable(String value) {
        this.value = new StringBuilder(value);
    }

    public void concat(String string) {
        value.append(string);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Type getType() {
        return Type.STRING;
    }
}
