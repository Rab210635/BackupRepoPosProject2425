package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;
import spengergasse.at.sj2425scherzerrabar.persistence.converter.BookTypeConverter;

@Entity
@Table(name = "copy")
public class Copy {
    @EmbeddedId
    CopyId copyId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "copy_publisher", foreignKey = @ForeignKey(name = "FK_copy_publisher"))
    private Publisher publisher;

    @Column(columnDefinition = BookTypeConverter.COLUMN_DEFINITION)
    private BookType bookType;
    private Integer pageCount;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "copy_book", foreignKey = @ForeignKey(name = "FK_book_copy"))
    private Book book;


    public Copy(Publisher publisher, BookType bookType, Integer pageCount, Book book) {
        this.copyId = copyId;
        this.publisher = publisher;
        this.bookType = bookType;
        this.pageCount = pageCount;
        this.book = book;
    }

    public Copy() {}

    @Embeddable
    record CopyId (@GeneratedValue @NotNull Long id){}
}
