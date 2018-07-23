package runtime.variable;

import parser.Scope;
import parser.ast.Type;
import runtime.Schedulable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CatalogueVariable extends FileVariable implements Schedulable {

    public CatalogueVariable(Scope scope) {
        super(scope);
        subdirectories = new ArrayVariable();
        files = new ArrayVariable();
    }

    public ArrayVariable getSubdirectories() {
        return subdirectories;
    }

    public void addSubdirectory(Variable variable) {
        subdirectories.add(variable);
    }

    public void addFile(Variable variable) {
        files.add(variable);
    }

    public ArrayVariable getFiles() {
        return files;
    }

    @Override
    public void create() {
        if (!super.file.mkdir())
            throw new RuntimeException("Error. Catalogue " + this.name + " could not be created");

        for (Variable file : files.getArray()) {
            if (file instanceof FileVariable)
                ((FileVariable) file).create();
        }

        for (Variable subdir : subdirectories.getArray()) {
            if (subdir instanceof CatalogueVariable)
                ((CatalogueVariable) subdir).create();
        }
    }

    @Override
    public Type getType() {
        return Type.CATALOGUE;
    }
}
