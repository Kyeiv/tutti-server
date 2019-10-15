package pl.com.tutti.tuttiserver.rest.exception;

public class SpecializationNotExistsException extends RuntimeException {
    public SpecializationNotExistsException() {
    }

    public SpecializationNotExistsException(String message) {
        super(message);
    }
}
