package pl.com.tutti.tuttiserver.rest.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayloadResponse<T> extends BasicResponse {
    private T payload;
}
