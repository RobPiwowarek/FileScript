package parser.ast.instruction.access;

import parser.Scope;
import parser.ast.Identifier;
import parser.ast.Node;
import parser.ast.instruction.call.FunctionCall;
import runtime.variable.FileVariable;
import runtime.variable.Variable;

import java.lang.reflect.Method;

public class MemberAccess extends Access {
    public MemberAccess(Node from) {
        this.from = from;
        this.access = new EmptyAccess();
    }

    @Override
    public Variable execute(Scope scope) {
        if (evaluatedOwner == null) {
            Variable result = from.execute(scope);
            return evaluate(scope, result);
        } else {
            return evaluate(scope, evaluatedOwner);
        }
    }

    private Variable evaluate(Scope scope, Variable evaluatedOwner) {
        if (evaluatedOwner instanceof FileVariable) {
            if (from instanceof FunctionCall) {
                String methodName = ((FunctionCall) from).getIdentifier().getName();
                Method[] methods = evaluatedOwner.getClass().getMethods();

                for (Method method : methods) {
                    if (method.getName().equals(methodName)) {
                        try {
                            Variable result = (Variable) method
                                    .invoke(evaluatedOwner, ((FunctionCall) from)
                                            .getArguments()
                                            .stream()
                                            .map(x -> x.execute(scope))
                                            .toArray());
                            access.setOwner(result);
                            return access.execute(scope);
                        } catch (Exception e) {
                            throw new RuntimeException("Error: member method not found"); // todo: better message
                        }
                    }
                }
                throw new RuntimeException("Error: member method not found");
            } else if (from instanceof Identifier) {
                String memberName = ((Identifier) from).getName();
                Variable result = ((FileVariable) evaluatedOwner).get(memberName);
                access.setOwner(result);
                return access.execute(scope);
            } else
                throw new RuntimeException("Error: unexpected type");
        } else
            throw new RuntimeException("Error: cannot access members of non-File variable");
    }
}