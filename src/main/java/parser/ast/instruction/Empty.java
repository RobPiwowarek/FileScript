package parser.ast.instruction;

import parser.Scope;
import runtime.variable.Variable;
import runtime.variable.VoidVariable;

public class Empty extends Instruction {
    private static Empty instance;

    public static Empty getInstance() {
        if (instance == null)
            instance = new Empty();

        return instance;
    }

    @Override
    public Variable execute(Scope scope) {
        return VoidVariable.getInstance();
    }
}
