package lexer.exception;

public class EmptySourceException extends RuntimeException {
    public EmptySourceException() {
        super("Input source is empty");
    }
}