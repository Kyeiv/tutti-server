package pl.com.tutti.tuttiserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.com.tutti.tuttiserver.entity.privatekey.AuthoritiesPK;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Authorities {

    @EmbeddedId
    private AuthoritiesPK authoritiesPK;
}
