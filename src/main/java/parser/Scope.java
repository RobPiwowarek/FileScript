package parser;

import parser.ast.Identifier;
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

    public void updateVariable(final String identifier, Variable newValue) {
        variables.replace(identifier, newValue);
    }

    public Variable getVariable(final String identifier) {
        return variables.get(identifier);
    }

    public Program getFunction(final String identifier) {
        return functions.get(identifier);
    }

    public Program getFunctionBody(Identifier identifier) {
        Program body = functions.get(identifier.getName());

        if (body == null)
            throw new RuntimeException("Function " + identifier.getName() + " not defined.");
        else
            return body;
    }

    private boolean containsVariable(String identifier) {
        boolean contains = false;

        if (parent != null)
            contains = parent.containsVariable(identifier);

        return contains || variables.containsKey(identifier);
    }

    private boolean containsFunction(String identifier) {
        boolean contains = false;

        if (parent != null)
            contains = parent.containsFunction(identifier);

        return contains || functions.containsKey(identifier);
    }

}
