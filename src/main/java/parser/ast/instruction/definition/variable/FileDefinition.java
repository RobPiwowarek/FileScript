package parser.ast.instruction.definition.variable;

import parser.ast.Node;
import parser.ast.Type;

import java.util.List;

// fileDefinition = fileType assignmentOperator '{' attribute { attribute } '}'
public class FileDefinition extends VariableDefinition {
     private Type type;
     private List<FileAttribute> attributes;

     public FileDefinition(String name, Type type, Node value, List<FileAttribute> attributes) {
          this.type = type;
          this.attributes = attributes;
          super.name = name;
          super.value = value;
     }

     public Type getType() {
          return type;
     }
}
