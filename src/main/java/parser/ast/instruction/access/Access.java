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



        return VoidVariable.getInstance();
    }
}
