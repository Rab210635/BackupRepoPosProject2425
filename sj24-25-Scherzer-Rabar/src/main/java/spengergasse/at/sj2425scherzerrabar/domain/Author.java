package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "author")
public class Author extends Person {
    private String penname;


    @ElementCollection
    @JoinTable(name = "addresses_in_authors", foreignKey = @ForeignKey(name = "FK_adresses_2_author"))
    protected List<Address> address;

    public Author(String firstName, String lastName, List<Address> address, EmailAddress emailAddress, String penname) {
        super(firstName, lastName, emailAddress);
        this.penname = penname;
        this.address = address;
    }

    public Author() {
        super();
    }
}
