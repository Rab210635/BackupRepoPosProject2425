package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "copy")
public class Copy {
    @EmbeddedId
    CopyId copyId;


    @Embeddable
    record CopyId (@GeneratedValue @NotNull Long id){}
}
