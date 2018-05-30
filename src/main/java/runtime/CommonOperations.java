package runtime;

import runtime.variable.BoolVariable;
import runtime.variable.IntegerVariable;
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

    public static IntegerVariable add(IntegerVariable a, IntegerVariable b) {
        return new IntegerVariable(a.getValue() + b.getValue());
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
