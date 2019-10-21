package pl.com.tutti.tuttiserver.rest.exception;

public class PostNotExistsException extends RuntimeException {
    public PostNotExistsException() {
        super();
    }

    public PostNotExistsException(String message) {
        super(message);
    }
}
