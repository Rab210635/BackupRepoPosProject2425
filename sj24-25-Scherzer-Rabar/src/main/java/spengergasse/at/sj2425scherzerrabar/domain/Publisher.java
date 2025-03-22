package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;
import spengergasse.at.sj2425scherzerrabar.foundation.ApiKeyFactory;

import java.io.Serializable;
@Entity
@Table(name = "publisher")
public class Publisher  {
    @EmbeddedId
    private PublisherId publisherId;
    @Embedded
    private ApiKey publisherApiKey;

    @NotNull
    private String name;
    @NotNull
    @Embedded
    private Address address;

    public Publisher() {
        this.publisherApiKey = new ApiKeyFactory().generate(30);
    }

    public Publisher(String name, Address address) {
        this.publisherApiKey = new ApiKeyFactory().generate(30);
        this.name = name;
        this.address = address;
    }

    @Embeddable
    record PublisherId (@GeneratedValue @NotNull Long id){}
}
