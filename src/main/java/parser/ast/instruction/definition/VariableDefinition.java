package parser.ast.instruction.definition;

import parser.ast.Node;

// variableDefinition = identifier ':' (arrayDefinition | fileDefinition | primitiveOrDateDefinition)
public abstract class VariableDefinition extends Definition {
     String name;
     Node value;
}
