package parser.ast.instruction.access;

import parser.ast.Node;
import runtime.variable.Variable;


/*
 *   Jaki jest zamysl i o co chodzi
 *   Generalnie problem z accessem jest taki, ze moge miec wiele kombinacji rzeczy ktore juz mam tzn. identifier.identifier.functioncall.identifier[0].functioncall
 *   Rozwiazanie jest proste, potrzeba w jakis sposob dostarczyc informacje do istniejacych node'ow o tym czy owner zostal juz wyliczony,
 *   tzn. w tym przykladzie identifier nr. 2 powinien dostac obiekt zmiennej identifier nr. 1 ktory jest juz wyliczony, zeby nie wyliczac go znowu.
 *   Te struktury sa proba implementacji rozwiazania tego problemu.
 */
public abstract class Access extends Node{
    protected Node from;
    protected Access access;
    protected Variable evaluatedOwner;

    void setOwner(Variable owner) {
        this.evaluatedOwner = owner;
    }

    public void setAccess(Access access) {
        this.access = access;
    }
}
