package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class BuyableBook{
        @EmbeddedId
        private BuyableBookId buyableBookId;
        @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(foreignKey = @ForeignKey(name = "FK_buyable_books_2_book"))
        private Book book;
        private BookType version;
        @Max(Integer.MAX_VALUE)
        @Min(1)
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
