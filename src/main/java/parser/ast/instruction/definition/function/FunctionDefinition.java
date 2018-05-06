package parser.ast.instruction.definition.function;

import parser.Program;
import parser.ast.Identifier;
import parser.ast.Node;
import parser.ast.instruction.definition.Definition;

import java.util.List;

// functionDefinition = "def" identifier '(' argumentsWithTypes ')' assignmentOperator instructionBlock
public class FunctionDefinition extends Definition {
    Identifier identifier;
    List<FunctionArgument> arguments;
    Program body;

    public FunctionDefinition(Identifier identifier, List<FunctionArgument> arguments, Program body) {
        this.identifier = identifier;
        this.arguments = arguments;
        this.body = body;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public List<FunctionArgument> getArguments() {
        return arguments;
    }

    public Program getBody() {
        return body;
    }
}
