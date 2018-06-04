package runtime.variable;

import parser.Program;
import parser.Scope;
import parser.ast.Type;
import runtime.Schedulable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileVariable extends Variable implements Schedulable {
    protected String name;
    protected Program updateBody;
    protected Map<String, Variable> attributes;
    protected File file;
    protected Scope scope;

    public FileVariable(Scope scope) {
        this.scope = scope;
        attributes = new HashMap<>();
    }

    public boolean open() {
        if (file == null || !file.exists())
            throw new RuntimeException("File named: " + name + " does not exist");
        else
            file = new File(name);

        return true;
    }

    public void setName(String name) {
        this.name = name;
        if (attributes.containsKey("name"))
            attributes.replace("name", new StringVariable(name));
        else
            attributes.put("name", new StringVariable(name));

        if (file == null)
            file = new File(name);
        else
            file.renameTo(new File(name));
    }

    public void create() {
        try {
            if (!file.exists())
                file.createNewFile(); // todo:
        } catch (IOException e) {
            e.printStackTrace(); // todo:
        }
    }

    private boolean moveTo(String destination) {
        try {
            Files.move(file.toPath(), Paths.get(destination));
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public IntegerVariable moveTo(Variable destination) {
        if (destination instanceof StringVariable){
            return moveTo(((StringVariable) destination).getValue().toString()) ? new IntegerVariable(0) : new IntegerVariable(1);
        } else if (destination instanceof CatalogueVariable) {
            return moveTo(((CatalogueVariable) destination).name) ? new IntegerVariable(0) : new IntegerVariable(1);
        } else {
            throw new RuntimeException("Invalid argument type for function moveTo. Expected String or Catalogue");
        }
    }

    public IntegerVariable copyTo(Variable destination) {
        if (destination instanceof StringVariable){
            return copyTo(((StringVariable) destination).getValue().toString()) ? new IntegerVariable(0) : new IntegerVariable(1);
        } else if (destination instanceof CatalogueVariable) {
            return copyTo(((CatalogueVariable) destination).name) ? new IntegerVariable(0) : new IntegerVariable(1);
        } else {
            throw new RuntimeException("Invalid argument type for function copyTo. Expected String or Catalogue");
        }
    }

    private boolean copyTo(String destination) {
        try {
            Files.copy(file.toPath(), Paths.get(destination));
            return true;
        } catch (IOException e) {
            e.printStackTrace(); // todo:
            return false;
        }
    }

    public void setUpdateBody(Program updateBody) {
        this.updateBody = updateBody;
    }

    @Override
    public void update() {
        updateBody.executeInstructions(scope);
    }

    @Override
    public Type getType() {
        return Type.FILE;
    }

    public Variable get(String attribute) {
        if (attributes.containsKey(attribute))
            return attributes.get(attribute);
        else
            throw new RuntimeException("Error: attribute " + attribute + " not found in file; " + name);
    }
}
