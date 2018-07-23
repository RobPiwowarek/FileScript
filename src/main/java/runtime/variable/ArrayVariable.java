package runtime.variable;

import parser.ast.Type;

import java.util.ArrayList;
import java.util.List;

public class ArrayVariable<TYPE> extends Variable {
    private List<TYPE> array;

    public ArrayVariable() {
        this.array = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder arrayString = new StringBuilder("[");

        array.forEach(element -> arrayString.append(element.toString() + ","));

        arrayString.replace(arrayString.length()-1, arrayString.length(), "]");

        return arrayString.toString();
    }

    @Override
    public Type getType() {
        return Type.ARRAY;
    }

    public TYPE get(int index) {
        return array.get(index);
    }
}