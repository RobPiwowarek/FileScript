package parser.ast.instruction.definition.variable;

import parser.Scope;
import parser.ast.Node;
import parser.ast.Type;
import runtime.variable.Variable;
import runtime.variable.VoidVariable;

// arrayDefinition = '[' [constInt] ']' type assignmentOperator (constArray | identifier)
public class ArrayDefinition extends VariableDefinition {
    private Type type;
    private int count;

    public ArrayDefinition(String name, Type type, Node value, int count) {
        this.type = type;
        this.count = count;
        super.name = name;
        super.value = value;
    }

    public ArrayDefinition(String name, Type type, Node value) {
        this(name, type, value, 0);
    }

    public Type getType() {
        return type;
    }

    @Override
    public Variable execute(Scope scope) {
        scope.addVariable(name, value.execute(scope));
        return VoidVariable.getInstance();
    }
}
