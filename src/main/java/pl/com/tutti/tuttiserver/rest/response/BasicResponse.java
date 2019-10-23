package pl.com.tutti.tuttiserver.rest.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicResponse {
    private int status;
    private  String message;
    private long timeStamp;
}
