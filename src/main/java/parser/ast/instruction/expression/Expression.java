package parser.ast.instruction.expression;

import lexer.token.TokenType;
import parser.Scope;
import parser.ast.Node;
import parser.ast.instruction.Empty;
import runtime.CommonOperations;
import runtime.Comparable;
import runtime.variable.BoolVariable;
import runtime.variable.IntegerVariable;
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
                if (leftResult instanceof Comparable && rightResult instanceof Comparable)
                    return CommonOperations.less((Comparable)leftResult, (Comparable)rightResult);
                else
                    throw new RuntimeException("Incomparable types");
            case LESS_EQUAL:
                if (leftResult instanceof Comparable && rightResult instanceof Comparable)
                    return CommonOperations.lessEqual((Comparable)leftResult, (Comparable)rightResult);
                else
                    throw new RuntimeException("Incomparable types");
            case EQUAL:
                if (leftResult instanceof Comparable && rightResult instanceof Comparable)
                    return CommonOperations.equal((Comparable)leftResult, (Comparable)rightResult);
                else
                    throw new RuntimeException("Incomparable types");
            case NOT_EQUAL:
                if (leftResult instanceof Comparable && rightResult instanceof Comparable)
                    return CommonOperations.notEqual((Comparable)leftResult, (Comparable)rightResult);
                else
                    throw new RuntimeException("Incomparable types");
            case GREATER:
                if (leftResult instanceof Comparable && rightResult instanceof Comparable)
                    return CommonOperations.greater((Comparable)leftResult, (Comparable)rightResult);
                else
                    throw new RuntimeException("Incomparable types");
            case GREATER_EQUAL:
                if (leftResult instanceof Comparable && rightResult instanceof Comparable)
                    return CommonOperations.greaterEqual((Comparable)leftResult, (Comparable)rightResult);
                else
                    throw new RuntimeException("Incomparable types");
            case AND:
                if (leftResult instanceof BoolVariable && rightResult instanceof BoolVariable)
                    return CommonOperations.and((BoolVariable)leftResult, (BoolVariable)rightResult);
                else
                    throw new RuntimeException("Incomparable types");
            case OR:
                if (leftResult instanceof BoolVariable && rightResult instanceof BoolVariable)
                    return CommonOperations.or((BoolVariable)leftResult, (BoolVariable)rightResult);
                else
                    throw new RuntimeException("Incomparable types");
            case PLUS:
                if (leftResult instanceof IntegerVariable && rightResult instanceof IntegerVariable)
                    return CommonOperations.add((IntegerVariable)leftResult, (IntegerVariable)rightResult);
                else
                    throw new RuntimeException("Cannot add these types");
            case MINUS:
                if (leftResult instanceof IntegerVariable && rightResult instanceof IntegerVariable)
                    return CommonOperations.subtract((IntegerVariable)leftResult, (IntegerVariable)rightResult);
                else
                    throw new RuntimeException("Cannot subtract these types");
            case MULTIPLY:
                if (leftResult instanceof IntegerVariable && rightResult instanceof IntegerVariable)
                    return CommonOperations.multiply((IntegerVariable)leftResult, (IntegerVariable)rightResult);
                else
                    throw new RuntimeException("Cannot multiply these types");
            case DIVIDE:
                if (leftResult instanceof IntegerVariable && rightResult instanceof IntegerVariable)
                    return CommonOperations.add((IntegerVariable)leftResult, (IntegerVariable)rightResult);
                else
                    throw new RuntimeException("Cannot divide these types");
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
