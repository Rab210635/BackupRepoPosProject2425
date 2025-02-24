package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import spengergasse.at.sj2425scherzerrabar.foundation.ApiKeyFactory;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "libraryorder")
public class Order {
    @EmbeddedId
    private OrderId id;
    @Embedded
    private ApiKey orderApiKey;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_customer_order"))
    private Customer customer;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "subscriptions_in_order",
            joinColumns = @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "FK_ordersubscription")),
            inverseJoinColumns = @JoinColumn(name = "subscription_id",foreignKey = @ForeignKey(name = "FK_subscription")))
    private List<LibrarySubscription> subscriptions;

    @NotNull
    @PastOrPresent
    private Date date;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_in_order",
            joinColumns = @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "FK_book")),
            inverseJoinColumns = @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_orderbook"))
    )
    private List<BuyableBook> books;

    public Order() {
        this.orderApiKey = new ApiKeyFactory().generate(30);
    }
    public Order(Customer customer,  List<LibrarySubscription> subscriptions, Date date, List<BuyableBook> books) {
        this.customer = customer;
        this.subscriptions = subscriptions;
        this.date = date;
        this.books = books;
        this.orderApiKey = new ApiKeyFactory().generate(30);

    }

    @Embeddable
    record OrderId(@GeneratedValue @NotNull Long id){}
}
