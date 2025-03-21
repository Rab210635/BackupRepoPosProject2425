package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;
import spengergasse.at.sj2425scherzerrabar.foundation.ApiKeyFactory;
import spengergasse.at.sj2425scherzerrabar.persistence.converter.BookGenreConverter;
import spengergasse.at.sj2425scherzerrabar.persistence.converter.BookTypeConverter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "book")
public class Book {
    @EmbeddedId
    private BookId bookId;
    @Embedded
    private ApiKey bookApiKey;
    @NotNull
    private String name;
    private LocalDate releaseDate;
    @NotNull
    private Boolean availableOnline;
    @ElementCollection
    @JoinTable(name = "BookTypes", foreignKey = @ForeignKey(name = "FK_book_types_2_book"))
    @Column(columnDefinition = BookTypeConverter.COLUMN_DEFINITION)
    private List<BookType> bookTypes;
    @NotNull
    @Min(100)
    @Max(Integer.MAX_VALUE)
    private Integer wordCount;
    @ElementCollection
    @JoinTable(name = "genres_of_book", foreignKey = @ForeignKey(name = "FK_genres_2_book"))
    @Column(name = "genre_code", columnDefinition = BookGenreConverter.COLUMN_DEFINITION)
    private List<BookGenre> genres;
    private String description;

    @ManyToMany
    @JoinTable(name = "authors_of_book", joinColumns = @JoinColumn(name = "book_id",
            foreignKey = @ForeignKey(name = "FK_books_2_authors")),
            inverseJoinColumns = @JoinColumn(name = "author_id",
                    foreignKey = @ForeignKey(name = "FK_authors_2_books"))
    )
    private List<Author> authors;

    public Book() {
        this.bookApiKey = new ApiKeyFactory().generate(30);
    }

    public Book(String name, LocalDate releaseDate, Boolean availableOnline, Integer wordCount, List<BookGenre> genres, List<Author> authors, List<BookType> bookTypes) {
        this.bookApiKey = new ApiKeyFactory().generate(30);
        this.name = name;
        this.releaseDate = releaseDate;
        this.availableOnline = availableOnline;
        this.wordCount = wordCount;
        this.genres = genres;
        this.authors = authors;
        this.bookTypes = bookTypes;
    }

    public void addAuthor(Author a){
        this.authors.add(a);
    }

    @Embeddable
    public record BookId (@GeneratedValue @NotNull Long id){}
    

}
