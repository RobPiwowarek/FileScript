package parser.ast.instruction.conditional;

import parser.Program;
import parser.ast.Node;
import parser.ast.instruction.Instruction;

// if = "if" '('expression')' instructionBlock ["else" instructionBlock]
public class If extends Instruction {
    Node expression;
    Program body;
    Else elseBlock;

    public If(Node expression, Program body, Else elseBlock) {
        this.expression = expression;
        this.body = body;
        this.elseBlock = elseBlock;
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
