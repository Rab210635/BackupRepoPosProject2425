package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "book")
public class Book {
    @EmbeddedId
    BookId bookId;


    @Embeddable
    record BookId (@GeneratedValue @NotNull Long id){}
}
