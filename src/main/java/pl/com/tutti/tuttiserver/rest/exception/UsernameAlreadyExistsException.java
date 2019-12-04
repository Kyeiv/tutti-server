package pl.com.tutti.tuttiserver.rest.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException() {
    }

    public UsernameAlreadyExistsException(String s) {
        super(s);
    }
}
