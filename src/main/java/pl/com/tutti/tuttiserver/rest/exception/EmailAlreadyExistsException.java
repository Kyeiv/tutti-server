package pl.com.tutti.tuttiserver.rest.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
    }

    public EmailAlreadyExistsException(String s) {
        super(s);
    }
}
