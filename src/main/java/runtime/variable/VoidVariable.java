package runtime.variable;

import parser.ast.Type;

public class VoidVariable extends Variable {
    private static VoidVariable instance;

    public static VoidVariable getInstance() {
        if (instance == null)
            instance = new VoidVariable();

        return instance;
    }

    @Override
    Type getType() {
        return Type.VOID;
    }
}
