import lexer.Scanner;
import parser.Parser;
import parser.Program;
import parser.Scope;

import java.io.File;

public class Interpreter {
    private Parser parser;
    private Scope globalScope = new Scope(null);
    private Program program;

    public Interpreter() {
        Scanner lexer = new Scanner(new File(System.getProperty("resources") + "test.file"));
        parser = new Parser(lexer);
        execute();
    }

    public void execute() {
        try {
            parser.parse().executeInstructions(globalScope);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
