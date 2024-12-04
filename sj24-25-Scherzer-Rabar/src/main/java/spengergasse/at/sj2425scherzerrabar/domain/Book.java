package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "book")
public class Book {
    @EmbeddedId
    private BookId bookId;







    private String name;
    private Date releaseDate;
    private Boolean availableOnline;
    private Integer wordCount;
    @ElementCollection
    @JoinTable(name = "GenresOfBook", foreignKey = @ForeignKey(name = "FK_Book_Genre"))
    private List<BookGenre> genres;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    public Book() {}

    public Book(String name, Date releaseDate, Boolean availableOnline, Integer wordCount, List<BookGenre> genres, List<Author> authors) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.availableOnline = availableOnline;
        this.wordCount = wordCount;
        this.genres = genres;
        this.authors = authors;

    }

    @Embeddable
    public record BookId (@GeneratedValue @NotNull Long id){}
}
