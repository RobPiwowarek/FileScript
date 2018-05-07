package parser.ast.instruction.definition.variable;

import parser.Scope;
import parser.ast.Node;
import runtime.variable.Variable;
import runtime.variable.VoidVariable;

// attribute = identifier ':' (constValue | identifier)
public class FileAttribute extends Node {
    private String name;
    private Node value;

    public FileAttribute(String name, Node value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Variable execute(Scope scope) {
        return VoidVariable.getInstance();
    }

    public String getName() {
        return name;
    }

    public Node getValue() {
        return value;
    }
}
