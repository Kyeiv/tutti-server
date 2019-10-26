package pl.com.tutti.tuttiserver.rest.controller.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.com.tutti.tuttiserver.rest.response.BasicResponse;
import pl.com.tutti.tuttiserver.rest.response.PayloadResponse;

public class ResponseFactory {
    public static <T> ResponseEntity createPayloadResponse(T payload, String message) {
        PayloadResponse<T> payloadResponse = new PayloadResponse<>();
        payloadResponse.setPayload(payload);
        payloadResponse.setMessage(message);
        payloadResponse.setStatus(HttpStatus.OK.value());
        payloadResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(payloadResponse, HttpStatus.OK);
    }

    public static ResponseEntity createBasicResponse(String message) {
        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setMessage(message);
        basicResponse.setStatus(HttpStatus.OK.value());
        basicResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }
}
