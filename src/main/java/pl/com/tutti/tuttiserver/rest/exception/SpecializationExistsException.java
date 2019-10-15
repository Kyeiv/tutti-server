package pl.com.tutti.tuttiserver.rest.exception;

public class SpecializationExistsException extends RuntimeException {
    public SpecializationExistsException() {
        super();
    }

    public SpecializationExistsException(String message) {
        super(message);
    }
}
