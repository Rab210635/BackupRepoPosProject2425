package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "libraryorder")
public class Order {
    @EmbeddedId
    private OrderId id;
    @ManyToOne()
    private Customer customer;
    @ManyToMany
    @JoinTable(name = "subscriptions_in_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id"))
    private List<LibrarySubscription> subscriptions;

    private Date date;

    @ManyToMany
    @JoinTable(
            name = "book_in_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
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
