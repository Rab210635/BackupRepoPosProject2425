package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;

@Entity
public class Review {

    @EmbeddedId
    ReviewId reviewId;

    private String title;
    private Integer rating;
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Customer customer;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Book book;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Branch branch;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Publisher publisher;

    // Constructor, getters, setters, and any other necessary methods

    public Review(String title, Integer rating, String description, Customer customer, Book book, Branch branch, Publisher publisher) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.customer = customer;
        this.book = book;
        this.branch = branch;
        this.publisher = publisher;
    }

    public Review() {}

    record ReviewId(Long reviewId) {

    }
    // Getters and setters


}
