package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import spengergasse.at.sj2425scherzerrabar.foundation.ApiKeyFactory;

@Entity
@Table(name = "buyableBook")
public class BuyableBook extends BookSpecification {
        @EmbeddedId
        private BuyableBookId buyableBookId;
        @Embedded
        private ApiKey buyableBookApiKey;

        private Float price;

        @Embeddable
        record BuyableBookId(@GeneratedValue @NotNull Long buyableBookId) {}

        public BuyableBook(){
                this.buyableBookApiKey = new ApiKeyFactory().generate(30);
        }
        public BuyableBook(Publisher publisher, BookType bookType, Integer pageCount, Book book, Float price) {
                super(publisher, bookType, pageCount, book);
                this.price = price;
                this.buyableBookApiKey = new ApiKeyFactory().generate(30);
                this.book = book;
        }

        @Override
        public Float getPrice() {
                return price;
        }

        @Override
        public void setPrice(Float price) {
                this.price = price;
        }

        public ApiKey getBuyableBookApiKey() {
                return buyableBookApiKey;
        }

        public void setBuyableBookApiKey(ApiKey buyableBookApiKey) {
                this.buyableBookApiKey = buyableBookApiKey;
        }
}
