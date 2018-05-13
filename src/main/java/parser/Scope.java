package parser;

import runtime.function.Function;
import runtime.variable.Variable;

import java.util.HashMap;
import java.util.Map;

public class Scope {
    private Scope parent;
    private Map<String, Function> functions;
    private Map<String, Variable> variables;

    public Scope(Scope parent) {
        this.parent = parent;
        functions = new HashMap<>();
        variables = new HashMap<>();
    }

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

    public Function getFunction(final String identifier) {
        return functions.get(identifier);
    }

    public void updateFunction(final String identifier, Function newFunction) {
        functions.replace(identifier, newFunction);
    }

    public boolean addFunction(final String identifier, Function function) {
        if (functions.containsKey(identifier))
            return false;
        else {
            functions.put(identifier, function);
            return true;
        }
    }

    public boolean containsIdentifier(String identifier) {
        return containsVariable(identifier) || containsFunction(identifier);
    }

    public boolean containsVariable(String identifier) {
        boolean contains = false;

        if (parent != null)
            contains = parent.containsVariable(identifier);

        return contains || variables.containsKey(identifier);
    }

    public boolean containsFunction(String identifier) {
        boolean contains = false;

        if (parent != null)
            contains = parent.containsFunction(identifier);

        return contains || functions.containsKey(identifier);
    }

}
