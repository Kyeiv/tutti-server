package pl.com.tutti.tuttiserver.rest.exception;

public class AvailbilityExistsException extends RuntimeException {
    public AvailbilityExistsException() {
        super();
    }

    public AvailbilityExistsException(String message) {
        super(message);
    }
}
