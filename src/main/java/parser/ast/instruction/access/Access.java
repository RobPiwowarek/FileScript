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

    public Access(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Variable execute(Scope scope) {
        FileVariable from = (FileVariable) left.execute(scope); // a moze byc zwrocony array variable i robimy tu access ewentualnie inna klasa na to

        if (left instanceof Identifier) {
            String memberName = ((Identifier) left).getName();

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
        } else if (left instanceof FunctionCall) {
            switch (((FunctionCall) left).getIdentifier().getName()) {
                case "copyTo":
                    break;
                case "moveTo":
                    break;
                case "delete":
                    break;
                default:
                    throw new RuntimeException("Error. Method undefined");
            }
        } else if (left instanceof Access) {

        } else
            throw new RuntimeException("Error. Expected Identifier, FunctionCall or Access as right side of Access operator");

        return null;
    }
}
