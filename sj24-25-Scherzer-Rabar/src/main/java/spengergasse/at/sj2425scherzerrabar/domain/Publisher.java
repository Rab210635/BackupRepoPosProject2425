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

    public PublisherId getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(PublisherId publisherId) {
        this.publisherId = publisherId;
    }

    public ApiKey getPublisherApiKey() {
        return publisherApiKey;
    }

    public void setPublisherApiKey(ApiKey publisherApiKey) {
        this.publisherApiKey = publisherApiKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
