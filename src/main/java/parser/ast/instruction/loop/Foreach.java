package parser.ast.instruction.loop;

import parser.Program;
import parser.Scope;
import parser.ast.Identifier;
import parser.ast.instruction.Instruction;
import runtime.variable.ArrayVariable;
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
        ArrayVariable array = (ArrayVariable) collection.execute(scope);
        scope.addVariable(iterator.getName(), VoidVariable.getInstance());

        for (Variable variable : array.getArray()) {
            scope.updateVariable(iterator.getName(), variable);
            body.executeInstructions(scope);
        }

        scope.removeVariable(iterator.getName());
        
        return VoidVariable.getInstance();
    }
}
