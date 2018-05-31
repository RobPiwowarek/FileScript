package parser.ast.instruction.call;

import parser.Scope;
import parser.ast.Identifier;
import parser.ast.Node;
import parser.ast.instruction.Instruction;
import runtime.CommonOperations;
import runtime.variable.Variable;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

// functionCall = identifier '(' arguments ')'
public class FunctionCall extends Instruction {
    private List<Node> arguments;
    private Identifier identifier;

    public FunctionCall(List<Node> arguments, Identifier identifier) {
        this.arguments = arguments;
        this.identifier = identifier;
    }

    @Override
    public Variable execute(Scope scope) {
        if (scope.containsFunction(identifier.getName())) {
            return scope.getFunction(identifier.getName()).call(arguments);
        } else {
            Class[] argumentClasses;

            List<Variable> evaluatedArguments = arguments.stream().map(x -> x.execute(scope)).collect(Collectors.toList());

            argumentClasses =
                    evaluatedArguments
                            .stream()
                            .map(Variable::getClass)
                            .map(Class::getSuperclass)
                            .toArray(Class[]::new);

            try {
                Method method = CommonOperations.class.getMethod(identifier.getName(), argumentClasses);
                return (Variable) method.invoke(null, evaluatedArguments.toArray());
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Error: could not find function: " + identifier.getName());
            } catch (Exception e) {
                StringBuilder stringBuilder =
                        new StringBuilder("Error: could not find method ")
                                .append(identifier.getName())
                                .append(" with argument types:");

                for (Class argumentClass : argumentClasses) {
                    stringBuilder
                            .append(" ")
                            .append(argumentClass.getCanonicalName());
                }

                throw new RuntimeException(stringBuilder.toString());
            }
        }
    }

    public Identifier getIdentifier() {
        return identifier;
    }
}