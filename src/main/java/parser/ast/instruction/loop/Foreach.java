package parser.ast.instruction.loop;

import parser.Program;
import parser.ast.Identifier;
import parser.ast.instruction.Instruction;

// foreach = "foreach" '('identifier ":" identifier')' instructionBlock
public class Foreach extends Instruction {
    Identifier iterator;
    Identifier collection;
    Program body;

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
}
