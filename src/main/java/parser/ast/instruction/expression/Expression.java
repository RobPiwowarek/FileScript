package parser.ast.instruction.expression;

import lexer.token.TokenType;
import parser.Scope;
import parser.ast.Executable;
import parser.ast.Node;
import parser.ast.instruction.Empty;

// expression = T {logicalOperator T}
// T = T2 {relationalOperator T2}
// T2 = T3 {lowPriorityArithmeticOperator T3}
// T3 = operand {highPriorityArithmeticOperator operand}
// operand = identifier | constValue | functionCall | '(' expression ')'
public class Expression extends Node implements Executable {
    Node left;
    TokenType operator;
    Node right;

    public Expression(Node left, TokenType operator, Node right) {
        if (left == null)
            throw new RuntimeException("Left side of an expression must not be null");

        this.left = left;

        this.operator = operator;

        if (right == null)
            this.right = Empty.getInstance();
        else
            this.right = right;
    }

    @Override
    public void execute(Scope scope) {
        if (left instanceof Executable) {
            ((Executable) left).execute(scope);
        }

    }

    public Node getLeft() {
        return left;
    }

    public TokenType getOperator() {
        return operator;
    }

    public Node getRight() {
        return right;
    }
}
