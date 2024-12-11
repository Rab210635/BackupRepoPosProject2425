package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "librarysubscription")
public class LibrarySubscription {
    @EmbeddedId
    LibrarySubscriptionId librarySubscriptionId;
    private String name;
    private String description;
    private Double monthlyCost;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "library_subscriptions_2_libraries"))
    private Library library;

    public LibrarySubscription() {}

    public LibrarySubscription(String name, String description, Double monthlyCost, Library library) {
        this.name = name;
        this.description = description;
        this.monthlyCost = monthlyCost;
        this.library = library;
    }

    @Embeddable
    record LibrarySubscriptionId (@GeneratedValue @NotNull Long id){}
}
