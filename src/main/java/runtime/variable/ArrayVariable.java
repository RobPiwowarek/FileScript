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

    public Variable get(int index) {
        return array.get(index);
    }

    public void add(int index, Variable element) {
        if (element.getType() == type)
            array.add(index, element);
        else
            throw new RuntimeException("Error: Cannot add variable of type " + element.getType() + " to array of type: " + type);
    }

    public void set(int index, Variable element) {
        if (element.getType() == type)
            array.set(index, element);
        else
            throw new RuntimeException("Error: Cannot set variable of type " + element.getType() + " to array of type: " + type);
    }

    public void add(Variable element) {
        if (element.getType() == type)
            array.add(element);
        else
            throw new RuntimeException("Error: Cannot add variable of type " + element.getType() + " to array of type: " + type);
    }
}