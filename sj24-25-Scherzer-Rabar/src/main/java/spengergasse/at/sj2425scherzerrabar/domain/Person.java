package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.List;

@MappedSuperclass
public class Person {
    @EmbeddedId
    protected PersonId personId;
    protected String firstName;
    protected String lastName;

    @ElementCollection
    protected List<Address> address;

    @Embedded
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_email_person"))
    protected Email email;


    @Embeddable
    public record PersonId(
        @GeneratedValue @NotNull Long id){
    }

    public Person(String firstName, String lastName, List<Address> address, Email mail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = mail;
    }
    public Person() {}

}
