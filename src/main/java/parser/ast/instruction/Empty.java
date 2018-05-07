package parser.ast.instruction;

import parser.Scope;
import parser.ast.Executable;
import parser.ast.Node;

public class Empty extends Instruction {
    private static Empty instance;

    public static Empty getInstance() {
        if (instance == null)
            instance = new Empty();

        return instance;
    }

    @Override
    public Executable execute(Scope scope) {
        return null;
    }
}
