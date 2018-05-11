package runtime.variable;

import parser.Scope;
import parser.ast.Type;
import runtime.Schedulable;

import java.util.ArrayList;
import java.util.List;

public class CatalogueVariable extends FileVariable implements Schedulable {
    private List<CatalogueVariable> subdirectories;
    private List<FileVariable> files;

    public CatalogueVariable(Scope scope) {
        super(scope);
        subdirectories = new ArrayList<>();
        files = new ArrayList<>();
    }

    public List<CatalogueVariable> getSubdirectories() {
        return subdirectories;
    }

    public void setSubdirectories(List<CatalogueVariable> subdirectories) {
        this.subdirectories = subdirectories;
    }

    public void addSubdirectories(List<Variable> variables) {
        for (Variable subdir : variables) {
            subdirectories.add((CatalogueVariable) subdir);
        }
    }

    public void addFiles(List<Variable> variables) {
        for (Variable file : variables) {
            files.add((FileVariable) file);
        }
    }

    public List<FileVariable> getFiles() {
        return files;
    }

    public void setFiles(List<FileVariable> files) {
        this.files = files;
    }

    @Override
    public void create() {
        if (!isOpened)
            return;

        super.file.mkdir();

        for (FileVariable file : files) {
            file.create();
        }

        for (CatalogueVariable subdir : subdirectories) {
            subdir.create();
        }
    }

    @Override
    public Type getType() {
        return Type.CATALOGUE;
    }
}
