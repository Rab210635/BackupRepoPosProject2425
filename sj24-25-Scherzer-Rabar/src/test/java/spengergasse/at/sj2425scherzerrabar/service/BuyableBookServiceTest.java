package spengergasse.at.sj2425scherzerrabar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.commands.BuyableBookCommand;
import spengergasse.at.sj2425scherzerrabar.domain.*;
import spengergasse.at.sj2425scherzerrabar.dtos.BuyableBookDto;
import spengergasse.at.sj2425scherzerrabar.persistence.BookRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.BuyableBookRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.PublisherRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class BuyableBookServiceTest {
    private @Mock BuyableBookRepository buyableBookRepository;
    private @Mock PublisherRepository publisherRepository;
    private @Mock BookRepository bookRepository;

    private BuyableBookService buyableBookService;

    @BeforeEach
    void setUp() {
        assumeThat(buyableBookRepository).isNotNull();
        assumeThat(publisherRepository).isNotNull();
        assumeThat(bookRepository).isNotNull();
        buyableBookService = new BuyableBookService(buyableBookRepository, publisherRepository, bookRepository);
    }

    @Test
    void can_create_buyable_book() {
        var publisher = FixturesFactory.publisher(FixturesFactory.address2());
        var book = FixturesFactory.book(FixturesFactory.author());

        when(publisherRepository.findPublisherByPublisherApiKey(any())).thenReturn(Optional.of(publisher));
        when(bookRepository.findBookByBookApiKey(any())).thenReturn(Optional.of(book));
        when(buyableBookRepository.save(any(BuyableBook.class))).then(AdditionalAnswers.returnsFirstArg());

        BuyableBookDto buyableBook = buyableBookService.createBuyableBook(
                new BuyableBookCommand(new ApiKey("BuyableBookApiKey").apiKey(),10f,publisher.getPublisherApiKey().apiKey(),
                        BookType.HARDCOVER.name(), 100,book.getBookApiKey().apiKey()));

        assertThat(buyableBook).isNotNull();
        assertThat(buyableBook.bookApiKey()).isEqualTo(book.getBookApiKey().apiKey());
        assertThat(buyableBook.publisherApiKey()).isEqualTo(publisher.getPublisherApiKey().apiKey());
    }

    @Test
    void cant_create_buyable_book_with_missing_publisher() {
        var publisher = FixturesFactory.publisher(FixturesFactory.address2());
        var book = FixturesFactory.book(FixturesFactory.author());

        when(bookRepository.findBookByBookApiKey(any())).thenReturn(Optional.of(book));

       assertThatThrownBy(() ->buyableBookService.createBuyableBook(new BuyableBookCommand(new ApiKey("BuyableBookApiKey").apiKey(),10f,
               publisher.getPublisherApiKey().apiKey(), BookType.HARDCOVER.name(), 100,book.getBookApiKey().apiKey())))
               .isInstanceOf(NoSuchElementException.class)
               .hasMessageContaining("Publisher not found");
    }

    @Test
    void cant_create_buyable_book_with_missing_book() {
        var publisher = FixturesFactory.publisher(FixturesFactory.address2());
        var book = FixturesFactory.book(FixturesFactory.author());

        assertThatThrownBy(() ->buyableBookService.createBuyableBook(new BuyableBookCommand(new ApiKey("BuyableBookApiKey").apiKey(),10f,
                publisher.getPublisherApiKey().apiKey(), BookType.HARDCOVER.name(), 100,book.getBookApiKey().apiKey())))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Book not found");
    }

    @Test
    void can_update_buyable_book() {
        var publisher = FixturesFactory.publisher(FixturesFactory.address2());
        var book = FixturesFactory.book(FixturesFactory.author());
        var buyableBook = FixturesFactory.buyableBook();

        when(buyableBookRepository.findBuyableBookByBuyableBookApiKey(any())).thenReturn(Optional.of(buyableBook));
        when(publisherRepository.findPublisherByPublisherApiKey(any())).thenReturn(Optional.of(publisher));
        when(bookRepository.findBookByBookApiKey(any())).thenReturn(Optional.of(book));
        when(buyableBookRepository.save(any(BuyableBook.class))).then(AdditionalAnswers.returnsFirstArg());

        BuyableBookDto updatedBuyableBook = buyableBookService.updateBuyableBook(
                new BuyableBookCommand(new ApiKey("BuyableBookApiKey").apiKey(),10f,publisher.getPublisherApiKey().apiKey(),
                        BookType.HARDCOVER.name(), 100,book.getBookApiKey().apiKey()));

        assertThat(updatedBuyableBook).isNotNull();
        assertThat(updatedBuyableBook.bookType()).isEqualTo(BookType.HARDCOVER.name());
    }

    @Test
    void cant_update_not_existing_buyable_book() {
        var publisher = FixturesFactory.publisher(FixturesFactory.address2());
        var book = FixturesFactory.book(FixturesFactory.author());

        assertThatThrownBy(()->buyableBookService.updateBuyableBook(
                new BuyableBookCommand(new ApiKey("BuyableBookApiKey").apiKey(),10f,publisher.getPublisherApiKey().apiKey(),
                        BookType.HARDCOVER.name(), 100,book.getBookApiKey().apiKey())))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Buyable Book not found");
    }

    @Test
    void cant_update_buyable_book_with_missing_publisher() {
        var publisher = FixturesFactory.publisher(FixturesFactory.address2());
        var book = FixturesFactory.book(FixturesFactory.author());
        var buyableBook = FixturesFactory.buyableBook();

        when(buyableBookRepository.findBuyableBookByBuyableBookApiKey(any())).thenReturn(Optional.of(buyableBook));
        when(bookRepository.findBookByBookApiKey(any())).thenReturn(Optional.of(book));

        assertThatThrownBy(()->buyableBookService.updateBuyableBook(
                new BuyableBookCommand(new ApiKey("BuyableBookApiKey").apiKey(),10f,publisher.getPublisherApiKey().apiKey(),
                        BookType.HARDCOVER.name(), 100,book.getBookApiKey().apiKey())))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Publisher not found");
    }

    @Test
    void cant_update_buyable_book_with_missing_book() {
        var publisher = FixturesFactory.publisher(FixturesFactory.address2());
        var book = FixturesFactory.book(FixturesFactory.author());
        var buyableBook = FixturesFactory.buyableBook();

        when(buyableBookRepository.findBuyableBookByBuyableBookApiKey(any())).thenReturn(Optional.of(buyableBook));

        assertThatThrownBy(()->buyableBookService.updateBuyableBook(
                new BuyableBookCommand(new ApiKey("BuyableBookApiKey").apiKey(),10f,publisher.getPublisherApiKey().apiKey(),
                        BookType.HARDCOVER.name(), 100,book.getBookApiKey().apiKey())))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Book not found");
    }

    @Test
    void can_delete_buyable_book() {
        BuyableBook buyableBook = FixturesFactory.buyableBook();
        when(buyableBookRepository.findBuyableBookByBuyableBookApiKey(any())).thenReturn(Optional.of(buyableBook));

        buyableBookService.deleteBuyableBook(new ApiKey("validApiKey").apiKey());

        verify(buyableBookRepository, times(1)).delete(buyableBook);
    }

    @Test
    void cant_delete_non_existing_buyable_book() {
        assertThatThrownBy(()->buyableBookService.deleteBuyableBook(new ApiKey("validApiKey").apiKey()));
    }

    @Test
    void can_get_existing_buyable_book_by_id(){
        BuyableBook buyableBook = FixturesFactory.buyableBook();
        when(buyableBookRepository.findBuyableBookByBuyableBookApiKey(any())).thenReturn(Optional.of(buyableBook));
        var buyableBook1 = buyableBookService.getBuyableBookByApiKey(buyableBook.getBuyableBookApiKey().apiKey());
        assertThat(buyableBook1).isEqualTo(BuyableBookDto.buyableBookDtoFromBuyableBook(buyableBook));
        verify(buyableBookRepository, times(1)).findBuyableBookByBuyableBookApiKey(any());
    }

    @Test
    void cant_get_not_existing_buyable_book_by_id() {
        assertThatThrownBy(()->buyableBookService.getBuyableBookByApiKey(new ApiKey("authorApiKey").apiKey()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Buyable Book not found");
    }

    @Test
    void can_get_buyable_books() {
        BuyableBook buyableBook = FixturesFactory.buyableBook();
        when(buyableBookRepository.findAll()).thenReturn(List.of(buyableBook,buyableBook));

        var buyableBooks = buyableBookService.getAllBuyableBooks();
        assertThat(buyableBooks).hasSize(2);
    }

    @Test
    void can_get_buyable_book_by_price() {
        BuyableBook buyableBook = FixturesFactory.buyableBook();
        BuyableBook buyableBook1 = FixturesFactory.buyableBook();
        when(buyableBookRepository.findAllByPrice(10f)).thenReturn(List.of(buyableBook,buyableBook1));

        var buyableBooks = buyableBookService.getAllBuyableBooksByPrice(10f);
        assertThat(buyableBooks).hasSize(2);
    }

    @Test
    void can_get_buyable_book_by_book_type() {
        BuyableBook buyableBook = FixturesFactory.buyableBook();
        BuyableBook buyableBook1 = FixturesFactory.buyableBook();
        when(buyableBookRepository.findAllByBookType(BookType.EBOOK)).thenReturn(List.of(buyableBook,buyableBook1));

        var buyableBooks = buyableBookService.getAllBuyableBooksByBookType(BookType.EBOOK.name());
        assertThat(buyableBooks).hasSize(2);
    }

    @Test
    void can_get_buyable_book_by_book() {
        BuyableBook buyableBook = FixturesFactory.buyableBook();
        BuyableBook buyableBook1 = FixturesFactory.buyableBook();
        Book book = FixturesFactory.book(FixturesFactory.author());

        when(bookRepository.findBookByBookApiKey(any())).thenReturn(Optional.of(book));
        when(buyableBookRepository.findAllByBook_BookApiKey_ApiKey("bookApiKey")).thenReturn(List.of(buyableBook,buyableBook1));

        var buyableBooks = buyableBookService.getAllBuyableBooksByBook("bookApiKey");
        assertThat(buyableBooks).hasSize(2);
    }

    @Test
    void cant_get_buyable_book_by_book_with_missing_book() {
        assertThatThrownBy(()->buyableBookService.getAllBuyableBooksByBook("bookApiKey"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Book not found");
    }

    @Test
    void can_get_buyable_book_by_publisher() {
        BuyableBook buyableBook = FixturesFactory.buyableBook();
        BuyableBook buyableBook1 = FixturesFactory.buyableBook();
        Publisher publisher = FixturesFactory.publisher(FixturesFactory.address2());

        when(publisherRepository.findPublisherByPublisherApiKey(any())).thenReturn(Optional.of(publisher));
        when(buyableBookRepository.findAllByPublisher_PublisherApiKey_ApiKey("publisherApiKey")).thenReturn(List.of(buyableBook,buyableBook1));

        var buyableBooks = buyableBookService.getAllBuyableBooksByPublisher("publisherApiKey");
        assertThat(buyableBooks).hasSize(2);
    }

    @Test
    void cant_get_buyable_book_by_publisher_with_missing_publisher() {
        assertThatThrownBy(()->buyableBookService.getAllBuyableBooksByPublisher("publisherApiKey"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Publisher not found");
    }
}