package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;
@Entity
@Table(name = "publisher")
public class Publisher  {
    @EmbeddedId
    PublisherId publisherId;


    @Embeddable
    record PublisherId (@GeneratedValue @NotNull Long id){}
}
