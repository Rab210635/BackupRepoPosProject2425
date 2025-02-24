package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;
import spengergasse.at.sj2425scherzerrabar.foundation.ApiKeyFactory;

import java.util.List;

@Entity
@Table(name = "library")
public class Library {
    @EmbeddedId
    private LibraryId libraryId;
    @NotNull
    private String name;
    @Embedded
    private ApiKey libraryApiKey;

    @NotNull
    private Address headquarters;

    @ElementCollection
    @JoinTable(name = "books_in_library",
            joinColumns = @JoinColumn(foreignKey = @ForeignKey(name = "FK_books_in_libraries_2_library")))
    private List<BookInLibraries> booksInLibraries;

    public Library() {
        this.libraryApiKey = new ApiKeyFactory().generate(30);

    }

    public Library( String name, Address headquarters, List<BookInLibraries> booksInLibraries) {
        this.libraryApiKey = new ApiKeyFactory().generate(30);
        this.name = name;
        this.headquarters = headquarters;
        this.booksInLibraries = booksInLibraries;
    }

    @Embeddable
    record LibraryId (@GeneratedValue @NotNull Long id){}

}
