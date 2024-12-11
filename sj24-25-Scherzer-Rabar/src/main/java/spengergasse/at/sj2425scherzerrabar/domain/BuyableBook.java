package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class BuyableBook{
        @EmbeddedId
        private BuyableBookId buyableBookId;
        @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        private Book book;
        private BookType version;
        private Float price;

        @Embeddable
        record BuyableBookId(@GeneratedValue @NotNull Long buyableBookId) {}

        public BuyableBook(){

        }
        public BuyableBook(Book book, BookType version, Float price) {
                this.book = book;
                this.version = version;
                this.price = price;
        }
}
