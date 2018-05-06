package parser.ast.instruction.value;

import java.text.SimpleDateFormat;

// constDate = DD '/' MM '/' YYYY [':' hour ':' minute ':' second]
public class ConstDate extends ConstValue {

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("DD'/'MM'/'YYYY");
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");


}
