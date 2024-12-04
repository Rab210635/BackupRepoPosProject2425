package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "librarysubscription")
public class LibrarySubscription {
    @EmbeddedId
    LibrarySubscriptionId librarySubscriptionId;


    @Embeddable
    record LibrarySubscriptionId (@GeneratedValue @NotNull Long id){}
}
