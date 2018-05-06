package parser.ast.instruction.value;

// constInt = digit { digit }
public class ConstInt extends ConstValue {
    private int value;

    public ConstInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
