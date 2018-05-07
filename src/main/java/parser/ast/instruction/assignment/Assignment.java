package parser.ast.instruction.assignment;

import parser.Scope;
import parser.ast.Executable;
import parser.ast.Identifier;
import parser.ast.Node;
import parser.ast.instruction.Instruction;

// assignment = identifier assignmentOperator assignableExpression
public class Assignment extends Instruction {
    Node expression;
    Identifier identifier;

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
    public Executable execute(Scope scope) {
        if (expression instanceof Executable)
            ((Executable) expression).execute(scope);
    }
}
