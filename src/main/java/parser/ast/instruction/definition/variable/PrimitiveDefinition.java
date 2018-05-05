package parser.ast.instruction.definition.variable;

import parser.ast.Node;
import parser.ast.Type;

// primitiveOrDateDefinition = (simpleType | dateType) assignmentOperator (constValue | identifier)
public class PrimitiveDefinition extends VariableDefinition {
     private Type type;

     public PrimitiveDefinition(String name, Type type, Node value) {
          this.type = type;
          super.name = name;
          super.value = value;
     }

     public Type getType() {
          return type;
     }
}
