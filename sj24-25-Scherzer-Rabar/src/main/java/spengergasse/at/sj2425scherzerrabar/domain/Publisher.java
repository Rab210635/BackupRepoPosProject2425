package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;
@Entity
@Table(name = "publisher")
public class Publisher  {
    @EmbeddedId
    private PublisherId publisherId;
    @NotNull
    private String name;
    @NotNull
    private Address address;

    public Publisher() {}

    public Publisher(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Embeddable
    record PublisherId (@GeneratedValue @NotNull Long id){}
}
