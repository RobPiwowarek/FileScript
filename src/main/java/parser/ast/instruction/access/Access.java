package parser.ast.instruction.access;

import parser.Scope;
import parser.ast.Identifier;
import parser.ast.Node;
import parser.ast.instruction.Instruction;
import parser.ast.instruction.call.FunctionCall;
import runtime.variable.*;

import java.util.List;
import java.util.stream.Collectors;

public class Access extends Instruction {
    private Node left;
    private Node right;

    public Access(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Variable execute(Scope scope) {
        Variable from = left.execute(scope);

        if (from instanceof ArrayVariable) {
            if (right instanceof FunctionCall) {
                List<Variable> args = ((FunctionCall) right).getArguments().stream().map(arg -> arg.execute(scope)).collect(Collectors.toList());

                switch (((FunctionCall) right).getIdentifier().getName()) {
                    case "get":
                        if (args.size() == 1)
                            return ((ArrayVariable) from).get(((IntegerVariable) args.get(0)).getValue());
                        else
                            throw new RuntimeException("Error. Too many arguments for function array get");
                    case "add":
                        if (args.size() == 1) {
                            ((ArrayVariable) from).add(args.get(0));
                            return VoidVariable.getInstance();
                        } else if (args.size() == 2) {
                            ((ArrayVariable) from).add(((IntegerVariable) args.get(0)).getValue(), args.get(1));
                            return VoidVariable.getInstance();
                        } else
                            throw new RuntimeException("Error. Too many arguments for function array get");
                    default:
                        throw new RuntimeException("Error. Undefined function");
                }
            }
        } else if (from instanceof FileVariable) {
            // todo: co jesli left jest Accessem?
            if (right instanceof Identifier) {
                String memberName = ((Identifier) right).getName();

                switch (memberName) {
                    case "subdirectories":
                        return ((CatalogueVariable) from).getSubdirectories();
                    case "files":
                        return ((CatalogueVariable) from).getFiles();
                    case "name":
                        return ((FileVariable) from).get("name");
                    default:
                        throw new RuntimeException("Error. Attribute not yet supported or undefined");
                }
            } else if (right instanceof FunctionCall) {
                switch (((FunctionCall) right).getIdentifier().getName()) {
                    case "create":
                        ((FileVariable) from).create();
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
        }

        return VoidVariable.getInstance();
    }
}
