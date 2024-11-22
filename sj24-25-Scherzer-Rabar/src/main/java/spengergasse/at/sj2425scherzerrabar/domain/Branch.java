package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;



@Entity
@Table(name = "branch")
public class Branch {
    @EmbeddedId
    BranchId branchId;


    @Embeddable
    record BranchId (@GeneratedValue @NotNull Long id){}
}
