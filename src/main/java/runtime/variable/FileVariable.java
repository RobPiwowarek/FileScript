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

public class FileVariable extends Variable implements Schedulable {
    protected String name;
    protected Program updateBody;
    protected File file;
    protected Scope scope;
    protected boolean isOpened = false;

    public  FileVariable(Scope scope) {
        this.scope = scope;
    }

    public boolean open() {
        if (!isOpened)
            isOpened = true;

        if (file == null || !file.exists())
            throw new RuntimeException("File named: " + name + " does not exist");
        else
            file = new File(name);

        return true;
    }

    // todo: what if file == null
    public boolean close() {
        if (isOpened) {
            isOpened = false;
            return true;
        } else
            return false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void create() {
        try {
            if (!file.exists())
                file.createNewFile(); // todo:
        } catch (IOException e) {
            e.printStackTrace(); // todo:
        }
    }

    public boolean rename(String newName) {
        return file.renameTo(new File(newName));
    }

    public boolean copyTo(String destination) {
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
}
