package pl.com.tutti.tuttiserver.rest.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.com.tutti.tuttiserver.rest.response.BasicResponse;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleException(Exception exc) {

        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setMessage(exc.getMessage());
        basicResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        basicResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
    }
}
