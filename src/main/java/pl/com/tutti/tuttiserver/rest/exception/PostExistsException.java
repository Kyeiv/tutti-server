package pl.com.tutti.tuttiserver.rest.exception;

public class PostExistsException extends RuntimeException {
    public PostExistsException() {
        super();
    }

    public PostExistsException(String message) {
        super(message);
    }
}
