package parser.ast.instruction.definition.variable;

import parser.Scope;
import parser.ast.Node;
import parser.ast.Type;
import runtime.variable.Variable;
import runtime.variable.VoidVariable;

// primitiveOrDateDefinition = (simpleType | dateType) assignmentOperator (constValue | identifier)
public class PrimitiveDefinition extends VariableDefinition {
    private Type type;

    public PrimitiveDefinition(String name, Type type, Node value) {
        this.type = type;
        super.name = name;
        super.value = value;
    }

    @Override // todo: refactor
    public Variable execute(Scope scope) {
        switch (type) {
            case INT:
            case BOOL:
            case STRING:
            case DATE:
                scope.addVariable(super.name, value.execute(scope));
                break;
            default:
                throw new RuntimeException("Error: Expected primitive type");
        }
        return VoidVariable.getInstance();
    }

    public Type getType() {
        return type;
    }
}
