package pl.com.tutti.tuttiserver.rest.exception;

public class BadDatesValuesException extends RuntimeException {
    public BadDatesValuesException() {
        super();
    }

    public BadDatesValuesException(String message) {
        super(message);
    }
}
