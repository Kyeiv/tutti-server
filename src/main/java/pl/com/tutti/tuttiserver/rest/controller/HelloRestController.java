package pl.com.tutti.tuttiserver.rest.controller;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

    @GetMapping("/")
    public MessageDto hello() {
        return new MessageDto("Hello world");
    }

    @GetMapping("/secured")
    public MessageDto helloSecured() {
        return new MessageDto("Hello secured");
    }
}

@AllArgsConstructor
@Setter
@Getter
class MessageDto{
    String message;
}