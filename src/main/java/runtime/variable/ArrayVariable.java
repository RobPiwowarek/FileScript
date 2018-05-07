package runtime.variable;

import java.util.ArrayList;
import java.util.List;

public class ArrayVariable extends Variable {
    private List<Variable> array;

    public ArrayVariable() {
        this.array = new ArrayList<>();
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
}
