package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import spengergasse.at.sj2425scherzerrabar.foundation.ApiKeyFactory;

@Entity
@Table(name = "branch")
public class Branch {
    @EmbeddedId
    BranchId branchId;
    @Embedded
    private ApiKey branchApiKey;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_branches_2_library"))
    private Library library;
    private Address address;

    @Embeddable
    record BranchId (@GeneratedValue @NotNull Long id){}

    public Branch() {
        this.branchApiKey = new ApiKeyFactory().generate(30);
    }

    public Branch( Library library, Address address) {
        this.branchApiKey = new ApiKeyFactory().generate(30);
        this.library = library;
        this.address = address;
    }
}
