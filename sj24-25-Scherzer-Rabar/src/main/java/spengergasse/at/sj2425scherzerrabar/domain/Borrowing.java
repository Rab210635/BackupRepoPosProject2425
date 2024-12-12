package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "borrowing")
public class Borrowing {
    @EmbeddedId
    private BorrowingId borrowingId;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "customer_borrowing", foreignKey = @ForeignKey(name = "FK_borrowings_2_customer"))
    private Customer customer;

    @JoinColumn(name = "copies_borrowed", foreignKey = @ForeignKey(name = "FK_borrowing_2_copies"))
    @OneToMany(cascade = CascadeType.ALL)
    private List<Copy> copies;
    @PastOrPresent
    private Date fromDate;
    @Max(30)
    @Min(1)
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
