package parser.ast.instruction.assignment;

import parser.Scope;
import parser.ast.Identifier;
import parser.ast.Node;
import parser.ast.instruction.Instruction;
import runtime.variable.Variable;
import runtime.variable.VoidVariable;

// assignment = identifier assignmentOperator assignableExpression
public class Assignment extends Instruction {
    private Node expression;
    private Identifier identifier;

    public Assignment(Node expression, Identifier identifier) {
        this.expression = expression;
        this.identifier = identifier;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Node getExpression() {
        return expression;
    }

    @Override
    public Variable execute(Scope scope) {
        Variable result = expression.execute(scope);
        if (scope.containsVariable(identifier.getName()))
            scope.updateVariable(identifier.getName(), result);
        else
            scope.addVariable(identifier.getName(), result);

        return VoidVariable.getInstance();
    }
}
