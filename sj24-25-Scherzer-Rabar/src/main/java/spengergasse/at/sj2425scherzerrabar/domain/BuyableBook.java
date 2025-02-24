package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import spengergasse.at.sj2425scherzerrabar.foundation.ApiKeyFactory;

@Entity
public class BuyableBook{
        @EmbeddedId
        private BuyableBookId buyableBookId;
        @Embedded
        private ApiKey buyableBookApiKey;
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
                this.buyableBookApiKey = new ApiKeyFactory().generate(30);
        }
        public BuyableBook(Book book, BookType version, Float price) {
                this.buyableBookApiKey = new ApiKeyFactory().generate(30);
                this.book = book;
                this.version = version;
                this.price = price;
        }
}
