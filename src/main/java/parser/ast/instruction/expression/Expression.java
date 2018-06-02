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

// expression = T {logicalOperator T}
// T = T2 {relationalOperator T2}
// T2 = T3 {lowPriorityArithmeticOperator T3}
// T3 = operand {highPriorityArithmeticOperator operand}
// operand = identifier | constValue | functionCall | '(' expression ')'
public class Expression extends Node {
    private Node left;
    private TokenType operator;
    private Node right;

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

        switch (operator) {
            case AND:
            case OR:
                return handleLogicalExpression((BoolVariable) leftResult, (BoolVariable) rightResult);
            case PLUS:
            case MINUS:
            case MULTIPLY:
            case DIVIDE:
                return handleArithmeticExpression(leftResult, rightResult);
            case LESS:
            case LESS_EQUAL:
            case GREATER:
            case GREATER_EQUAL:
            case EQUAL:
            case NOT_EQUAL:
                return handleRelationalExpression((Comparable) leftResult, (Comparable) rightResult);
            default:
                throw new RuntimeException("Error. Invalid operator");
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

    private Variable handleArithmeticExpression(Variable leftResult, Variable rightResult) {
        switch (operator) {
            case PLUS:
                return CommonOperations.add(leftResult, rightResult);
            case MINUS:
                return CommonOperations.subtract((IntegerVariable)leftResult, (IntegerVariable) rightResult);
            case MULTIPLY:
                return CommonOperations.multiply((IntegerVariable)leftResult, (IntegerVariable)rightResult);
            case DIVIDE:
                return CommonOperations.divide((IntegerVariable)leftResult, (IntegerVariable)rightResult);
            default:
                throw new RuntimeException("Unexpected operator in expression " + operator);
        }
    }

    private Variable handleLogicalExpression(BoolVariable leftResult, BoolVariable rightResult) {
        switch (operator) {
            case AND:
                return CommonOperations.and(leftResult, rightResult);
            case OR:
                return CommonOperations.or(leftResult, rightResult);
            default:
                throw new RuntimeException("Unexpected operator in expression " + operator);
        }
    }

    private Variable handleRelationalExpression(Comparable leftResult, Comparable rightResult) {
        switch (operator) {
            case LESS:
                return CommonOperations.less(leftResult, rightResult);
            case LESS_EQUAL:
                return CommonOperations.lessEqual(leftResult, rightResult);
            case EQUAL:
                return CommonOperations.equal(leftResult, rightResult);
            case NOT_EQUAL:
                return CommonOperations.notEqual(leftResult, rightResult);
            case GREATER:
                return CommonOperations.greater(leftResult, rightResult);
            case GREATER_EQUAL:
                return CommonOperations.greaterEqual(leftResult, rightResult);
            default:
                throw new RuntimeException("Unexpected operator in expression " + operator);
        }
    }
}
