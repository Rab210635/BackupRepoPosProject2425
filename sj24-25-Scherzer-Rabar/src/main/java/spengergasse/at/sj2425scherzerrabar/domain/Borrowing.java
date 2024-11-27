package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;

@Entity
@Table(name = "borrowing")
public class Borrowing {
    @EmbeddedId
    BorrowingId borrowingId;


    @Embeddable
    record BorrowingId (@GeneratedValue @NotNull Long id){}
}
