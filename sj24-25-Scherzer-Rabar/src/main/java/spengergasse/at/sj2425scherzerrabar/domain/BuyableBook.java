package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class BuyableBook{
        @EmbeddedId
        BuyableBookId buyableBookId;
        //TODO optionals? adden und wenn ja wann weil dann schlägt save immer aus :))
        @ManyToOne()
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
