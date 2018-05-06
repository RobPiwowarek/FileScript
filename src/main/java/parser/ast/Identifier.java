package parser.ast;

public class Identifier extends Node {
    String name;

    public Identifier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
