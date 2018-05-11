package runtime.variable;

import parser.ast.Type;

import java.util.ArrayList;
import java.util.List;

public class ArrayVariable extends Variable {
    private List<Variable> array;
    private Type type;

    public ArrayVariable(Type type) {
        this.array = new ArrayList<>();
        this.type = type;
    }

    public List<Variable> getArray() {
        return array;
    }

    public void setArray(List<Variable> array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return "array: TODO"; // todo:
    }

    @Override
    public Type getType() {
        return type;
    }
}
