package pl.com.tutti.tuttiserver.rest.exception;

public class AvailbilityNotExistsException extends RuntimeException {
    public AvailbilityNotExistsException() {
        super();
    }

    public AvailbilityNotExistsException(String message) {
        super(message);
    }
}
