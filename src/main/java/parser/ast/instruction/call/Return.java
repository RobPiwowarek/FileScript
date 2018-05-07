package parser.ast.instruction.call;

import parser.Scope;
import parser.ast.Node;
import parser.ast.instruction.Instruction;
import runtime.variable.Variable;

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
    public Variable execute(Scope scope) {
        return expression.execute(scope);
    }
}
