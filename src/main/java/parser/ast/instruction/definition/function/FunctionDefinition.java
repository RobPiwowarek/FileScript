package parser.ast.instruction.definition.function;

import parser.Program;
import parser.Scope;
import parser.ast.Identifier;
import parser.ast.Type;
import parser.ast.instruction.definition.Definition;
import runtime.function.Function;
import runtime.variable.*;

import java.util.List;

// functionDefinition = "def" identifier '(' argumentsWithTypes ')' assignmentOperator instructionBlock
public class FunctionDefinition extends Definition {
    private Identifier identifier;
    private List<FunctionArgument> arguments;
    private Program body;
    private Type returnType;

    public FunctionDefinition(Identifier identifier, List<FunctionArgument> arguments, Program body, Type returnType) {
        this.identifier = identifier;
        this.arguments = arguments;
        this.body = body;
        this.returnType = returnType;
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

    @Override
    public Variable execute(Scope scope) {
        if (scope.containsFunction(identifier.getName()))
            throw new RuntimeException("Error. Function " + identifier.getName() + " already defined.");

        Scope subScope = new Scope(scope);

        for (FunctionArgument argument : arguments) {
            Variable var;

            switch(argument.getType()){
                case FILE:
                    var = new FileVariable(scope);
                    break;
                case CATALOGUE:
                    var = new CatalogueVariable(scope);
                    break;
                case INT:
                    var = new IntegerVariable(Integer.MAX_VALUE);
                    break;
                case STRING:
                    var = new StringVariable("DEFAULT");
                    break;
                case BOOL:
                    var = new BoolVariable(false);
                    break;
                default:
                    throw new RuntimeException("ERROR. NOT YET IMPLEMENTED.");
            }

            subScope.addVariable(argument.getIdentifier(), var);
        }

        scope.addFunction(identifier.getName(), new Function(body, subScope, arguments, returnType));
        return VoidVariable.getInstance();
    }
}
