package parser.ast.instruction.access;

import parser.Scope;
import parser.ast.Identifier;
import parser.ast.Node;
import parser.ast.instruction.call.FunctionCall;
import runtime.variable.CatalogueVariable;
import runtime.variable.FileVariable;
import runtime.variable.Variable;

public class Access extends Node {
    private Node left;
    private Node right;

    @Override
    public Variable execute(Scope scope) {
        FileVariable from = (FileVariable) left.execute(scope); // a moze byc zwrocony array variable i robimy tu access ewentualnie inna klasa na to

        if (right instanceof Identifier) {
            String memberName = ((Identifier) right).getName();

            switch (memberName) {
                case "subdirectories":
                    return ((CatalogueVariable) from).getSubdirectories();
                case "files":
                    return ((CatalogueVariable) from).getFiles();
                case "name":
                    return from.get("name");
                default:
                    throw new RuntimeException("Error. Attribute not yet supported or undefined");
            }
        } else if (right instanceof FunctionCall) {
            // copy from, copy to
        } else
            throw new RuntimeException("Error. Expected Identifier, FunctionCall or Access as right side of Access operator");

        return null;
    }
}
