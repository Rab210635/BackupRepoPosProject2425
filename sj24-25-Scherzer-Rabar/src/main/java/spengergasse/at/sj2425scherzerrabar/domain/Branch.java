package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "branch")
public class Branch {
    @EmbeddedId
    BranchId branchId;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_branches_2_librarys"))
    private Library library;
    private Address address;

    @Embeddable
    record BranchId (@GeneratedValue @NotNull Long id){}

    public Branch() {}

    public Branch( Library library, Address address) {
        this.library = library;
        this.address = address;
    }
}
