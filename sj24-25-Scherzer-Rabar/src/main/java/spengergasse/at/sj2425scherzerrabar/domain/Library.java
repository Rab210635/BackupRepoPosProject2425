package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;
@Entity
@Table(name = "library")
public class Library {
    @EmbeddedId
    LibraryId libraryId;


    @Embeddable
    record LibraryId (@GeneratedValue @NotNull Long id){}

}
