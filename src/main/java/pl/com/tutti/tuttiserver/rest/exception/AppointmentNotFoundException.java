package pl.com.tutti.tuttiserver.rest.exception;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException() {
        super();
    }

    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
