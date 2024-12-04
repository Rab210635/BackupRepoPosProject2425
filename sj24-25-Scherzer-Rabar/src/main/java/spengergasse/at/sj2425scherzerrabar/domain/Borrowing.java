package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "borrowing")
public class Borrowing {
    @EmbeddedId
    private BorrowingId borrowingId;

    //TODO OPTIONAL EIG
    @ManyToOne(optional = true)
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Copy> copies;
    private Date fromDate;
    private int extendedByDays;

    public Borrowing() {}

    public Borrowing(Customer customer, List<Copy> copies, Date fromDate, int extendedByDays) {
        this.customer = customer;
        this.copies = copies;
        this.fromDate = fromDate;
        this.extendedByDays = extendedByDays;
    }


    @Embeddable
    record BorrowingId (@GeneratedValue @NotNull Long id){}
}
