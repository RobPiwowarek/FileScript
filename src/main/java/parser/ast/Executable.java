package parser.ast;

import parser.Scope;

public interface Executable {
    Executable execute(Scope scope);
}
