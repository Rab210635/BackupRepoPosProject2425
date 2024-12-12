package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "libraryorder")
public class Order {
    @EmbeddedId
    private OrderId id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_customer_order"))
    private Customer customer;
    @ManyToMany
    @JoinTable(name = "subscriptions_in_order",
            joinColumns = @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "FK_order")),
            inverseJoinColumns = @JoinColumn(name = "subscription_id",foreignKey = @ForeignKey(name = "FK_subscription")))
    private List<LibrarySubscription> subscriptions;

    @NotNull
    @PastOrPresent
    private Date date;

    @ManyToMany
    @JoinTable(
            name = "book_in_order",
            joinColumns = @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "FK_book")),
            inverseJoinColumns = @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_order"))
    )
    private List<BuyableBook> books;

    public Order() {}
    public Order(Customer customer,  List<LibrarySubscription> subscriptions, Date date, List<BuyableBook> books) {
        this.customer = customer;
        this.subscriptions = subscriptions;
        this.date = date;
        this.books = books;
    }

    @Embeddable
    record OrderId(@GeneratedValue @NotNull Long id){}
}
