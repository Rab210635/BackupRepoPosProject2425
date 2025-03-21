package spengergasse.at.sj2425scherzerrabar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.commands.BookCommand;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.domain.BookGenre;
import spengergasse.at.sj2425scherzerrabar.domain.BookType;
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
import static org.mockito.Mockito.when;

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
        when(authorRepository.findById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(()-> bookService.createBook(new BookCommand("name", LocalDate.now(),true, List.of(BookType.EBOOK),489,"cooler Book", List.of(100L),List.of(BookGenre.COMICS)))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void can_create_book(){
        var author = FixturesFactory.author();

        when(authorRepository.findById(any())).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).then(AdditionalAnswers.returnsFirstArg());

        var Book = bookService.createBook( new BookCommand(
                "name",LocalDate.of(2025,2,2),true,
                List.of(BookType.EBOOK),489,"cooler Book",
                List.of(100L),List.of(BookGenre.COMICS)));
        assertThat(Book).isNotNull();
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