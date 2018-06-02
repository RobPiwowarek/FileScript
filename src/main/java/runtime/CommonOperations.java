package runtime;

import parser.ast.Type;
import runtime.variable.BoolVariable;
import runtime.variable.IntegerVariable;
import runtime.variable.StringVariable;
import runtime.variable.Variable;

public abstract class CommonOperations {
    public static BoolVariable less(Comparable a, Comparable b) {
        return new BoolVariable(a.compare(b) == -1);
    }

    public static BoolVariable lessEqual(Comparable a, Comparable b) {
        int result = a.compare(b);
        return new BoolVariable(result == 0 || result == -1);
    }

    public static BoolVariable equal(Comparable a, Comparable b) {
        return new BoolVariable(a.compare(b) == 0);
    }

    public static BoolVariable notEqual(Comparable a, Comparable b) {
        return new BoolVariable(a.compare(b) != 0);
    }

    public static BoolVariable greater(Comparable a, Comparable b) {
        return new BoolVariable(a.compare(b) == 1);
    }

    public static BoolVariable greaterEqual(Comparable a, Comparable b) {
        int result = a.compare(b);
        return new BoolVariable(result == 1 || result == 0);
    }

    public static BoolVariable and(BoolVariable a, BoolVariable b) {
        return new BoolVariable(a.getValue() && b.getValue());
    }

    public static BoolVariable or(BoolVariable a, BoolVariable b) {
        return new BoolVariable(a.getValue() || b.getValue());
    }

    private static IntegerVariable add(IntegerVariable a, IntegerVariable b) {
        return new IntegerVariable(a.getValue() + b.getValue());
    }

    private static StringVariable add(StringVariable a, Variable b) {
        if (b instanceof IntegerVariable)
            return new StringVariable(a.getValue().append(String.valueOf(((IntegerVariable) b).getValue())).toString());
        else if (b instanceof BoolVariable)
            return new StringVariable(a.getValue().append(String.valueOf(((BoolVariable) b).getValue())).toString());
        else
            return new StringVariable(a.getValue().append(((StringVariable) b).getValue()).toString());
    }

    public static Variable add(Variable a, Variable b){
        if (a.getType() == Type.STRING)
            return add((StringVariable) a, b);
        else
            return add((IntegerVariable) a, (IntegerVariable) b);
    }

    public static IntegerVariable subtract(IntegerVariable a, IntegerVariable b) {
        return new IntegerVariable(a.getValue() - b.getValue());
    }

    public static IntegerVariable multiply(IntegerVariable a, IntegerVariable b) {
        return new IntegerVariable(a.getValue() * b.getValue());
    }

    public static IntegerVariable divide(IntegerVariable a, IntegerVariable b) {
        if (b.getValue() == 0)
            throw new ArithmeticException("Error. Division by 0");

        return new IntegerVariable(a.getValue() / b.getValue());
    }

    public static void println(Variable variable) {
        System.out.println(variable.toString());
    }
}
