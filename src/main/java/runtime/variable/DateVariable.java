package runtime.variable;

import java.text.SimpleDateFormat;
import java.time.Instant;

public class DateVariable extends Variable {
    private Instant value;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("DD'/'MM'/'YYYY");
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

    public DateVariable(String date) {

    }
}
