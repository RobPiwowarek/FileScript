package parser.ast.instruction.loop;

import parser.Program;
import parser.Scope;
import parser.ast.Identifier;
import parser.ast.instruction.Instruction;
import runtime.variable.Variable;
import runtime.variable.VoidVariable;

// foreach = "foreach" '('identifier ":" identifier')' instructionBlock
public class Foreach extends Instruction {
    private Identifier iterator;
    private Identifier collection;
    private Program body;

    public Foreach(Identifier iterator, Identifier collection, Program body) {
        this.iterator = iterator;
        this.collection = collection;
        this.body = body;
    }

    public Identifier getIterator() {
        return iterator;
    }

    public Identifier getCollection() {
        return collection;
    }

    public Program getBody() {
        return body;
    }

    @Override
    public Variable execute(Scope scope) {
        body.executeInstructions(scope);
        return VoidVariable.getInstance();
    }
}
