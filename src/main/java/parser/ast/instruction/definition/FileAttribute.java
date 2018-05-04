package parser.ast.instruction.definition;

import parser.ast.Node;
import parser.ast.Type;

// attribute = identifier ':' (constValue | identifier)
public class FileAttribute extends Node {
     private String name;
     private Node value;

     public FileAttribute(String name, Node value) {
          this.name = name;
          this.value = value;
     }

     public String getName() {
          return name;
     }

     public Node getValue() {
          return value;
     }
}
