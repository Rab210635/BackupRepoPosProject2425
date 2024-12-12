package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;
import spengergasse.at.sj2425scherzerrabar.persistence.converter.BookTypeConverter;

@Entity
@Table(name = "copy")
public class Copy {
    @EmbeddedId
    private CopyId copyId;

    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_copies_2_publisher"))
    private Publisher publisher;

    @NotNull
    @Column(columnDefinition = BookTypeConverter.COLUMN_DEFINITION)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_copy_2_book_type"))
    private BookType bookType;
    @NotNull
    @Min(3)
    @Max(Integer.MAX_VALUE)
    private Integer pageCount;
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_copies_2_book"))
    private Book book;


    public Copy(Publisher publisher, BookType bookType, Integer pageCount, Book book) {
        this.publisher = publisher;
        this.bookType = bookType;
        this.pageCount = pageCount;
        this.book = book;
    }

    public Copy() {}

    @Embeddable
    record CopyId (@GeneratedValue @NotNull Long id){}
}
