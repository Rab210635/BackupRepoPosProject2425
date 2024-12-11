package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;
import spengergasse.at.sj2425scherzerrabar.persistence.converter.BookGenreConverter;
import spengergasse.at.sj2425scherzerrabar.persistence.converter.BookTypeConverter;

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
    @ElementCollection
    @JoinTable(name = "BookTypes", foreignKey = @ForeignKey(name = "FK_Book_Type"))
    //TODO weg mit dem comment
    //@Column(columnDefinition = BookTypeConverter.COLUMN_DEFINITION)
    private List<BookType> bookTypes;
    private Integer wordCount;
    @ElementCollection
    @JoinTable(name = "GenresOfBook", foreignKey = @ForeignKey(name = "FK_Book_Genre"))
    @Column(name = "genre_code", columnDefinition = BookGenreConverter.COLUMN_DEFINITION)
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

    public Book(String name, Date releaseDate, Boolean availableOnline, Integer wordCount, List<BookGenre> genres, List<Author> authors, List<BookType> bookTypes) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.availableOnline = availableOnline;
        this.wordCount = wordCount;
        this.genres = genres;
        this.authors = authors;
        this.bookTypes = bookTypes;
    }

    @Embeddable
    public record BookId (@GeneratedValue @NotNull Long id){}
    

}
