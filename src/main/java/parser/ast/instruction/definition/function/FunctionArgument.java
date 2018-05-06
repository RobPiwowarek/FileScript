package parser.ast.instruction.definition.function;

import parser.ast.Identifier;
import parser.ast.Node;
import parser.ast.Type;

// argumentsWithTypes = { identifier ':' type [ ',' identifier ':' type ] }
public class FunctionArgument extends Node {
    Type type;
    Identifier identifier;

    public FunctionArgument(Type type, Identifier identifier) {
        this.type = type;
        this.identifier = identifier;
    }

    public Type getType() {
        return type;
    }

    public Identifier getIdentifier() {
        return identifier;
    }
}
