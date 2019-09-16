package pl.com.tutti.tuttiserver.rest.response.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.com.tutti.tuttiserver.rest.response.BasicResponse;
import pl.com.tutti.tuttiserver.rest.response.error.RegisterErrorResponse;

@ControllerAdvice
public class RegistrationExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<RegisterErrorResponse> handleException(Exception exc) {

        RegisterErrorResponse registerErrorResponse = new RegisterErrorResponse();
        registerErrorResponse.setMessage(exc.getMessage());
        registerErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        registerErrorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(registerErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
