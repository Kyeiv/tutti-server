package pl.com.tutti.tuttiserver.rest.response;

public class RegisterResponse {

    private Integer status;
    private String message;
    private Long timeStamp;

    public RegisterResponse(Integer status, String message, Long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public RegisterResponse() {
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }
}
