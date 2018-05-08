package runtime.variable;

import parser.ast.Type;
import runtime.Comparable;

import java.text.SimpleDateFormat;
import java.time.Instant;

public class DateVariable extends Variable implements Comparable {
    private Instant value;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("DD'/'MM'/'YYYY");
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

    public DateVariable(String date) {

    }

    @Override
    Type getType() {
        return Type.DATE;
    }

    @Override
    public int compare(Object object) {
        return 0; // todo:
    }
}
