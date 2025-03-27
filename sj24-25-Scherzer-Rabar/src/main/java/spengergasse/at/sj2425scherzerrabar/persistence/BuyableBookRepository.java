package spengergasse.at.sj2425scherzerrabar.persistence;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.domain.BookType;
import spengergasse.at.sj2425scherzerrabar.domain.BuyableBook;
import spengergasse.at.sj2425scherzerrabar.domain.Order;
import spengergasse.at.sj2425scherzerrabar.dtos.BuyableBookDto;

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

    @Query("""
        select b from BuyableBook b
            where b.buyableBookApiKey = :buyableBookApiKey
    """)
    public Optional<BuyableBookDto> getProjectedBuyableBookByBuyableBookApiKey(String buyableBookApiKey);

    @Query("""
    select b from BuyableBook b
    """)
    public List<BuyableBookDto> findAllProjected();

    @Query("""
    select b from BuyableBook b
        where exists (select p from b.publisher p where p.publisherApiKey = :publisherApiKey)
    """)
    public List<BuyableBookDto> getProjectedByPublisher(String publisherApiKey);

    @Query("""
    select b from BuyableBook b
        where b.bookType = :bookType
    """)
    public List<BuyableBookDto> getProjectedByBookType(BookType bookType);

    @Query("""
    select b from BuyableBook b
        where b.price = :price
    """)
    public List<BuyableBookDto> getProjectedByPrice(Float price);

    @Query("""
    select b from BuyableBook b
        where exists (select bb from b.book bb where bb.bookApiKey = :bookApiKey)
    """)
    public List<BuyableBookDto> getProjectedByBook(String bookApiKey);

}
