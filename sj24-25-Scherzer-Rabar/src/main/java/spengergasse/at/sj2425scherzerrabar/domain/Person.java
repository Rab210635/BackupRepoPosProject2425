package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;
@Entity
public class Person {
    @EmbeddedId
    PersonId personId;


    @Embeddable
    record PersonId (@GeneratedValue @NotNull Long id){}
}
