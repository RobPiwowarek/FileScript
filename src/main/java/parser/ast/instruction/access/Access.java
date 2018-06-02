package parser.ast.instruction.access;

import parser.Scope;
import parser.ast.Identifier;
import parser.ast.Node;
import parser.ast.instruction.Instruction;
import parser.ast.instruction.call.FunctionCall;
import runtime.variable.*;

public class Access extends Instruction {
    private Node left;
    private Node right;

    public Access(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Variable execute(Scope scope) {
        FileVariable from = (FileVariable) left.execute(scope); // a moze byc zwrocony array variable i robimy tu access ewentualnie inna klasa na to

        // todo: co jesli left jest Accessem?
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
            switch (((FunctionCall) right).getIdentifier().getName()) {
                case "create":
                    from.create();
                case "copyTo":
                    break;
                case "moveTo":
                    break;
                case "delete":
                    break;
                default:
                    throw new RuntimeException("Error. Method undefined");
            }
        } else if (right instanceof ArrayAccess) {
            Node accessFrom = ((ArrayAccess) right).getFrom();
            if (accessFrom instanceof Identifier) {
                switch (((Identifier) accessFrom).getName()) {
                    case "subdirectories":
                        return ((CatalogueVariable) from).getSubdirectories().get(((IntegerVariable) ((ArrayAccess) right).getIndex().execute(scope)).getValue());
                    case "files":
                        return ((CatalogueVariable) from).getFiles().get(((IntegerVariable) ((ArrayAccess) right).getIndex().execute(scope)).getValue());
                    default:
                        throw new RuntimeException("Error. Member " + ((Identifier) accessFrom).getName() + " undefined.");
                }
            } else if (accessFrom instanceof FunctionCall) {
                throw new RuntimeException("NYI Exception");
            } else
                throw new RuntimeException("Error. Expected Identifier or FunctionCall as ArrayAccess from");
        } else
            throw new RuntimeException("Error. Expected Identifier, FunctionCall or Access as right side of Access operator");

        return VoidVariable.getInstance();
    }
}
