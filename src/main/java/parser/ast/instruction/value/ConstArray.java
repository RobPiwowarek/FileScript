package parser.ast.instruction.value;

import parser.Scope;
import parser.ast.Node;
import parser.ast.Type;
import runtime.variable.ArrayVariable;
import runtime.variable.Variable;
import runtime.variable.VoidVariable;

import java.util.List;

// constArray = '[' { constValue { ',' constValue } ']'
public class ConstArray extends ConstValue {
    private List<Node> value;

    public ConstArray(List<Node> value) {
        this.value = value;
    }

    public List<Node> getValue() {
        return value;
    }

    @Override
    public Variable execute(Scope scope) {
        ArrayVariable array = null;
        boolean setType = false;

        for (Node node : value) {
            Variable var = node.execute(scope);

            if (!setType){
                array = new ArrayVariable(var.getType());
                setType = true;
            }

            array.add(var);
        }

        if (value.isEmpty())
            array = new ArrayVariable(Type.VOID);

        return array;
    }
}