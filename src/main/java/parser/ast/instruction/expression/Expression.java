package parser.ast.instruction.expression;

import lexer.token.TokenType;
import parser.Scope;
import parser.ast.Node;
import parser.ast.instruction.Empty;
import runtime.variable.Variable;
import runtime.variable.VoidVariable;

// expression = T {logicalOperator T}
// T = T2 {relationalOperator T2}
// T2 = T3 {lowPriorityArithmeticOperator T3}
// T3 = operand {highPriorityArithmeticOperator operand}
// operand = identifier | constValue | functionCall | '(' expression ')'
public class Expression extends Node {
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
    public Variable execute(Scope scope) {
        Variable leftResult = left.execute(scope);
        Variable rightResult = right.execute(scope);

        // todo:
        switch (operator) {
            case LESS:
            case LESS_EQUAL:
            case EQUAL:
            case NOT_EQUAL:
            case GREATER:
            case GREATER_EQUAL:
                throw new RuntimeException("to do exception");
            case AND:
            case OR:
                throw new RuntimeException("to do exception");
            case PLUS:
            case MINUS:
                throw new RuntimeException("to do exception");
            case MULTIPLY:
            case DIVIDE:
                throw new RuntimeException("to do exception");
        }

        return VoidVariable.getInstance();
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
