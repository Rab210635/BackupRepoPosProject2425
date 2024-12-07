package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;

@Embeddable
public class Subscribing {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_subscribings_2_branches"))
    private Branch mainBranch;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_subscribings_2_library_subscriptions"))
    private LibrarySubscription librarySubscription;
}
