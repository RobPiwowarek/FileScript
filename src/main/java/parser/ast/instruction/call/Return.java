package parser.ast.instruction.call;

import parser.Scope;
import parser.ast.Executable;
import parser.ast.Node;
import parser.ast.instruction.Instruction;

// return = "return" expression
public class Return extends Instruction {
    Node expression;

    public Return(Node expression) {
        this.expression = expression;
    }

    public Node getExpression() {
        return expression;
    }

    @Override
    public Executable execute(Scope scope) {
        if (expression instanceof Executable)
            ((Executable) expression).execute(scope);
    }

}
