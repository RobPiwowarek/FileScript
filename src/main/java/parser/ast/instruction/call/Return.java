package parser.ast.instruction.call;

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
}
