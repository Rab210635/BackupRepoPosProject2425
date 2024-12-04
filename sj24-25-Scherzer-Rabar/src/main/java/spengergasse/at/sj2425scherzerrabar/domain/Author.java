package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "author")
public class Author extends Person {
    private String penname;


    @ElementCollection
    @JoinTable(name = "addresses_in_authors", foreignKey = @ForeignKey(name = "FK_author_addresses"))
    protected List<Address> address;

    public Author(String firstName, String lastName, List<Address> address, EmailAddress emailAddress, String penname) {
        super(firstName, lastName, address, emailAddress);
        this.penname = penname;
    }

    public Author() {
        super();
    }
}
