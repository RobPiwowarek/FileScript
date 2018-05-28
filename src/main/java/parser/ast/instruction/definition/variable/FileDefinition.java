package parser.ast.instruction.definition.variable;

import parser.Scope;
import parser.ast.Type;
import runtime.variable.ArrayVariable;
import runtime.variable.CatalogueVariable;
import runtime.variable.FileVariable;
import runtime.variable.Variable;

import java.util.List;

// fileDefinition = fileType assignmentOperator '{' attribute { attribute } '}'
public class FileDefinition extends VariableDefinition {
    private Type type;
    private List<FileAttribute> attributes;

    public FileDefinition(String name, Type type, List<FileAttribute> attributes) {
        this.type = type;
        this.attributes = attributes;
        super.name = name;
    }

    @Override
    public Variable execute(Scope scope) {
        switch (type) {
            case FILE:
                FileVariable file = new FileVariable(scope);

                for (FileAttribute attribute : attributes) {
                    if (attribute.getName().equals("name"))
                        file.setName((attribute.getValue().execute(scope)).toString());
                    else
                        throw new RuntimeException("Error: Undefined attribute " + attribute.getName());
                }

                return file;
            case CATALOGUE:
                CatalogueVariable catalogue = new CatalogueVariable(scope);

                for (FileAttribute attribute : attributes) {
                    switch (attribute.getName()) {
                        case "name":
                            catalogue.setName((attribute.getValue().execute(scope)).toString());
                            break;
                        case "subdirectories":
                            for (Variable cata:((ArrayVariable) attribute.getValue().execute(scope)).getArray())
                                catalogue.addSubdirectory(cata);
                            break;
                        case "files":
                            for (Variable filee:((ArrayVariable) attribute.getValue().execute(scope)).getArray())
                                catalogue.addFile(filee);
                            break;
                        default:
                            throw new RuntimeException("Error: Undefined attribute " + attribute.getName());
                    }
                }

                return catalogue;
            default:
                throw new RuntimeException("Error: Expected File or Catalogue type");
        }
    }
}
