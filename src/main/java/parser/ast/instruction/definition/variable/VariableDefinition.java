package parser.ast.instruction.definition.variable;

import parser.ast.Node;
import parser.ast.instruction.definition.Definition;

// variableDefinition = identifier ':' (arrayDefinition | fileDefinition | primitiveOrDateDefinition)
public abstract class VariableDefinition extends Definition {
    String name;
    Node value;
}
