package pl.com.tutti.tuttiserver.rest.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostData {
    private Integer id;
    @NotNull
    private String title;
    @NotNull
    private String content;
    private LocalDateTime createdAt;
    private String username;
}
