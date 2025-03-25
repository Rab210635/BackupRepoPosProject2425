package spengergasse.at.sj2425scherzerrabar.persistence;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.domain.BookType;
import spengergasse.at.sj2425scherzerrabar.domain.BuyableBook;
import spengergasse.at.sj2425scherzerrabar.domain.Order;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface BuyableBookRepository extends JpaRepository<BuyableBook, Long> {
    public Optional<BuyableBook> findBuyableBookByBuyableBookApiKey(String apiKey);

    List<BuyableBook> findAllByPublisher_PublisherApiKey_ApiKey(String publisherPublisherApiKeyApiKey);

    List<BuyableBook> findAllByBook_BookApiKey_ApiKey(String bookBookApiKeyApiKey);

    List<BuyableBook> findAllByPrice(Float price);

    List<BuyableBook> findAllByBookType(@NotNull BookType bookType);
}
