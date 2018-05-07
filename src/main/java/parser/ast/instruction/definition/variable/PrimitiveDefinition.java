package parser.ast.instruction.definition.variable;

import parser.Scope;
import parser.ast.Executable;
import parser.ast.Node;
import parser.ast.Type;
import runtime.variable.BoolVariable;
import runtime.variable.DateVariable;
import runtime.variable.StringVariable;

// primitiveOrDateDefinition = (simpleType | dateType) assignmentOperator (constValue | identifier)
public class PrimitiveDefinition extends VariableDefinition {
    private Type type;

    public PrimitiveDefinition(String name, Type type, Node value) {
        this.type = type;
        super.name = name;
        super.value = value;
    }

    @Override
    public Executable execute(Scope scope) {


        switch (type) {
            case INT:
            case BOOL:
                BoolVariable bool;

                if(value instanceof Executable)
                    bool = ((Executable) value).execute(scope);

                scope.addVariable(super.name, bool);
                break;
            case STRING:
                StringVariable string;

                if(value instanceof Executable)
                    string = ((Executable) value).execute(scope);

                scope.addVariable(super.name, string);
                break;
            case DATE:
                DateVariable date;

                if(value instanceof Executable)
                    date = ((Executable) value).execute(scope);

                scope.addVariable(super.name, date);
                break;
            default:
                throw new RuntimeException("Error: Expected primitive type");
        }
    }

    public Type getType() {
        return type;
    }
}
