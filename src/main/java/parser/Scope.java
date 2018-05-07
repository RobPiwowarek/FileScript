package parser;

import parser.ast.Identifier;
import parser.ast.Node;
import parser.ast.instruction.definition.function.FunctionDefinition;
import runtime.variable.Variable;

import java.util.Map;

public class Scope {
    private Scope parent;
    private Map<String, Program> functions;
    private Map<String, Variable> variables;

    public boolean addVariable(final String identifier, Variable value) {
        if (variables.containsKey(identifier)) {
            return false;
        } else {
            variables.put(identifier, value);
            return true;
        }
    }

    public Program getFunctionBody(Identifier identifier){
        Program body = functions.get(identifier.getName());

        if (body == null)
            throw new RuntimeException("Function " + identifier.getName() + " not defined.");
        else
            return body;
    }


}
