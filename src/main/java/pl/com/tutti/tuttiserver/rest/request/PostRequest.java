package pl.com.tutti.tuttiserver.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}