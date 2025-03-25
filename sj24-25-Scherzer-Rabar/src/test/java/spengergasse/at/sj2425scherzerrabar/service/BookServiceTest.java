package spengergasse.at.sj2425scherzerrabar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.commands.BookCommand;
import spengergasse.at.sj2425scherzerrabar.domain.*;
import spengergasse.at.sj2425scherzerrabar.persistence.AuthorRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    private @Mock BookRepository bookRepository;
    private @Mock AuthorRepository authorRepository;

    private BookService bookService;


    @BeforeEach
    void setUp(){
        assumeThat(bookRepository).isNotNull();
        assumeThat(authorRepository).isNotNull();
        bookService = new BookService(bookRepository,authorRepository);
    }

    @Test
    void cant_create_book_with_missing_author(){
        when(authorRepository.findAuthorByAuthorApiKey(any())).thenReturn(Optional.empty());
        assertThatThrownBy(()-> bookService.createBook(new BookCommand(new ApiKey("BookApiKeys").apiKey(),"name", LocalDate.now(),true, List.of(BookType.EBOOK.name()),489,"cooler Book", List.of(new ApiKey("AuthorApiKey").apiKey()),List.of(BookGenre.COMICS.name())))).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void can_create_book(){
        var author = FixturesFactory.author();

        when(authorRepository.findAuthorByAuthorApiKey(any())).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).then(AdditionalAnswers.returnsFirstArg());

        var Book = bookService.createBook( new BookCommand(
                new ApiKey("bookApiKey").apiKey(),"name",LocalDate.of(2025,2,2),true,
                List.of(BookType.EBOOK.name()),489,"cooler Book",
                List.of(new ApiKey("authorApiKey").apiKey()),List.of(BookGenre.COMICS.name())));
        assertThat(Book).isNotNull();
    }

    @Test
    void cant_delete_non_existing_book() {
        when(bookRepository.findBookByBookApiKey(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.deleteBook(new ApiKey("invalidApiKey")))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void can_delete_existing_book() {

        var book = FixturesFactory.book(FixturesFactory.author());
        when(bookRepository.findBookByBookApiKey(any())).thenReturn(Optional.of(book));

        bookService.deleteBook(new ApiKey("validApiKey"));

        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void cant_update_non_existing_book() {
        when(bookRepository.findBookByBookApiKey(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.updateBook(new BookCommand(
                new ApiKey("bookApiKey").apiKey(), "Updated Name", LocalDate.now(), true,
                List.of(BookType.EBOOK.name()), 500, "Updated Description",
                List.of(new ApiKey("authorApiKey").apiKey()), List.of(BookGenre.COMICS.name()))))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void can_update_existing_book() {
        var author = FixturesFactory.author();
        var book = FixturesFactory.book(author);
        when(bookRepository.findBookByBookApiKey(any())).thenReturn(Optional.of(book));
        when(authorRepository.findAuthorByAuthorApiKey(any())).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).then(AdditionalAnswers.returnsFirstArg());

        var command = new BookCommand(
                new ApiKey("bookApiKey").apiKey(), "Updated Name", LocalDate.now(), true,
                List.of(BookType.EBOOK.name()), 500, "Updated Description",
                List.of(new ApiKey("authorApiKey").apiKey()), List.of(BookGenre.COMICS.name()));

        bookService.updateBook(command);

        assertThat(book.getName()).isEqualTo("Updated Name");
        assertThat(book.getDescription()).isEqualTo("Updated Description");
        assertThat(book.getAvailableOnline()).isTrue();
    }

    @Test
    void can_get_books_without_author_filter() {
        var book1 = FixturesFactory.book(FixturesFactory.author());
        var book2 = FixturesFactory.book(FixturesFactory.author());

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        var books = bookService.getBooks(Optional.empty());

        assertThat(books).hasSize(2);
    }

    @Test
    void can_get_books_by_author() {
        var author = FixturesFactory.author();
        var book1 = FixturesFactory.book(author);

        when(authorRepository.findAuthorByAuthorApiKey(any())).thenReturn(Optional.of(author));
        when(bookRepository.findByAuthorsContains(any())).thenReturn(List.of(book1));

        var books = bookService.getBooks(Optional.of(new ApiKey("authorApiKey")));

        assertThat(books).hasSize(1);
        assertThat(books.get(0).name()).isEqualTo("dasd");
    }

    @Test
    void cant_get_books_by_non_existing_author() {
        when(authorRepository.findAuthorByAuthorApiKey(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBooks(Optional.of(new ApiKey("invalidApiKey"))))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void can_get_single_book() {
        var author = FixturesFactory.author();
        var book = FixturesFactory.book(author);

        when(bookRepository.findBookByBookApiKey(any())).thenReturn(Optional.of(book));

        var bookDto = bookService.getBook(new ApiKey("bookApiKey"));

        assertThat(bookDto.name()).isEqualTo("dasd");
    }

    @Test
    void cant_get_non_existing_book() {
        when(bookRepository.findBookByBookApiKey(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBook(new ApiKey("invalidApiKey")))
                .isInstanceOf(NoSuchElementException.class);
    }

/*
    @Test
    void cant_create_book_with_invalid_date(){
        var author = FixturesFactory.author();
        when(authorRepository.findById(any())).thenReturn(Optional.of(author));
        assertThatThrownBy(()-> bookService.createBook( new BookCommand(
                "name",LocalDate.of(2025,2,2),true,
                List.of(BookType.EBOOK),489,"cooler Book",
                List.of(100L),List.of(BookGenre.COMICS))))
                .isInstanceOf(IllegalArgumentException.class);
    }
*/
}