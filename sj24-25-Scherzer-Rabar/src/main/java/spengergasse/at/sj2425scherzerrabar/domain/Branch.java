package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.List;


@Entity
@Table(name = "branch")
public class Branch {
    @EmbeddedId
    BranchId branchId;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_branches_2_librarys"))
    private Library library;
    private Address address;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_branches_2_copys"))
    protected List<Copy> copys;

    @Embeddable
    record BranchId (@GeneratedValue @NotNull Long id){}

    public Branch() {}

    public Branch( Library library, Address address, List<Copy> copys) {
        this.library = library;
        this.address = address;
        this.copys = copys;
    }
}
