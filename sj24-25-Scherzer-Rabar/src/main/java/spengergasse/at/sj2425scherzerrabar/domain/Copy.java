package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "copy")
public class Copy {
    @EmbeddedId
    CopyId copyId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Publisher publisher;

    private BookType bookType;
    private Integer pageCount;
    @ManyToOne(fetch = FetchType.LAZY)
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
