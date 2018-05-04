package parser.ast.instruction.value;

// constString = '"' { alpha | digit } '"'
public class ConstString extends ConstValue {
    private String value;

    public ConstString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
