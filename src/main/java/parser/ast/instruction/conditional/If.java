package parser.ast.instruction.conditional;

import parser.Program;
import parser.Scope;
import parser.ast.Executable;
import parser.ast.Node;
import parser.ast.instruction.Instruction;

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
    public void execute(Scope scope) {
        boolean isTrue = false;

        if (expression instanceof Executable)
            isTrue = ((Executable) expression).execute(scope);

        if (isTrue)
            body.executeInstructions(scope);
        else
            if(elseBlock != null)
                elseBlock.getBody().executeInstructions(scope);
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
