package parser.ast.instruction.value;

import parser.Scope;
import runtime.variable.Variable;

// constDate = DD '/' MM '/' YYYY [':' hour ':' minute ':' second]
public class ConstDate extends ConstValue {

    @Override
    public Variable execute(Scope scope) {
        return null; // todo:
    }
}
