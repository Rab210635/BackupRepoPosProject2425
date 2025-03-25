package spengergasse.at.sj2425scherzerrabar.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.domain.BuyableBook;
import spengergasse.at.sj2425scherzerrabar.domain.Order;

import java.util.Optional;

@Repository
public interface BuyableBookRepository extends JpaRepository<BuyableBook, Long> {
    public Optional<BuyableBook> findBuyableBookByBuyableBookApiKey(String apiKey);

}
