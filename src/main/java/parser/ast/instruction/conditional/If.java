package parser.ast.instruction.conditional;

import parser.Program;
import parser.Scope;
import parser.ast.Node;
import parser.ast.instruction.Instruction;
import runtime.variable.BoolVariable;
import runtime.variable.Variable;
import runtime.variable.VoidVariable;

// if = "if" '('expression')' instructionBlock ["else" instructionBlock]
public class If extends Instruction {
    private Node expression;
    private Program body;
    private Else elseBlock;

    public If(Node expression, Program body, Else elseBlock) {
        this.expression = expression;
        this.body = body;
        this.elseBlock = elseBlock;
    }

    @Override
    public Variable execute(Scope scope) {
        boolean isTrue;

        isTrue = ((BoolVariable) expression.execute(scope)).getValue();

        if (isTrue)
            body.executeInstructions(scope);
        else if (elseBlock != null)
            elseBlock.getBody().executeInstructions(scope);

        return VoidVariable.getInstance();
    }

    public Node getExpression() {
        return expression;
    }

    public Program getBody() {
        return body;
    }

    public Else getElseBlock() {
        return elseBlock;
    }
}
