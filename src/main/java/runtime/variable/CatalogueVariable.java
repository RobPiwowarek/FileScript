package runtime.variable;

import parser.Program;
import parser.Scope;
import runtime.Schedulable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CatalogueVariable extends Variable implements Schedulable {
    private String name;
    private Program updateBody;
    private File file;
    private Scope scope;
    private List<FileVariable> subdirectories;
    private boolean isOpened = false;

    public CatalogueVariable(String name, Scope scope) {
        this.name = name;
        this.scope = scope;

        file = new File(name);
    }

    public boolean open() {
        if (!isOpened)
            isOpened = true;

        return true;
    }

    public boolean close() {
        if (isOpened) {
            isOpened = false;
            return true;
        }
        else
            return false;
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
}
