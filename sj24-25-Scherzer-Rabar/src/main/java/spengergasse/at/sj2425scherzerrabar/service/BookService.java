package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spengergasse.at.sj2425scherzerrabar.commands.BookCommand;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.Author;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.dtos.BookDto;
import spengergasse.at.sj2425scherzerrabar.persistence.AuthorRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.BookRepository;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Book createBook(BookCommand command) {
        List<Author> authors = command.authorIds().stream()
                .map(x -> authorRepository.findAuthorByAuthorApiKey(x.apiKey()))
                .flatMap(Optional::stream)
                .toList();
        if (authors.isEmpty()) {
            throw new NoSuchElementException("Author not found");
        }
        Book book = new Book(
                command.name(), command.releaseDate(), command.availableOnline(), command.wordCount(), command.genre(), authors, command.types(), command.description()
        );
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(ApiKey bookApiKey) {
        Book book = bookRepository.findBookByBookApiKey(bookApiKey.apiKey())
                .orElseThrow(NoSuchElementException::new);
        bookRepository.delete(book);
    }


    @Transactional
    public void updateBook(BookCommand command) {
        bookRepository.findBookByBookApiKey(command.apiKey().apiKey()).map((Book b)->{
            if(!b.getName().equals(command.name()))
                b.setName(command.name());
            if(b.getAvailableOnline() != command.availableOnline())
                b.setAvailableOnline(command.availableOnline());
            if(b.getBookTypes() != command.types())
                b.setBookTypes(command.types());
            if(b.getGenres() != command.genre())
                b.setGenres(command.genre());
            if(!b.getDescription().equals(command.description()))
                b.setDescription(command.description());
            if(b.getReleaseDate() != command.releaseDate())
                b.setReleaseDate(command.releaseDate());

            List<Author> authors = command.authorIds().stream()
                    .map(Record::toString)
                    .map(authorRepository::findAuthorByAuthorApiKey)
                    .flatMap(Optional::stream)
                    .toList();
            if(authors.isEmpty()){
                throw new NoSuchElementException("Author not found");
            }
            b.setAuthors(authors);
            //if(b.getAuthors())
            bookRepository.save(b);
            return b;
        }).orElseThrow(NoSuchElementException::new);
    }

    public List<BookDto> getBooks(Optional<ApiKey> authorApiKey) {
        List<Book> books;
        if (authorApiKey.isPresent()) {
            Optional<Author> author = authorRepository.findAuthorByAuthorApiKey(String.valueOf(authorApiKey));
            if(author.isPresent()) {
                books = bookRepository.findByAuthorsContains(author.get());
            }else {
                throw new NoSuchElementException("Author not found");
            }
        } else {
            books = bookRepository.findAll();
        }
        return books.stream().map(book -> new BookDto(
                book.getBookApiKey(),
                book.getName(),
                book.getReleaseDate(),
                book.getAvailableOnline(),
                book.getBookTypes(),
                book.getWordCount(),
                book.getDescription(),
                book.getAuthors().stream().map(Author::getAuthorApiKey).toList(),
                book.getGenres()
        )).toList();
    }

    public BookDto getBook(ApiKey bookApiKey) {
        Optional<BookDto> returnValue = bookRepository.findBookByBookApiKey(bookApiKey.apiKey()).map((book -> {
            return new BookDto(
                    book.getBookApiKey(),
                    book.getName(),
                    book.getReleaseDate(),
                    book.getAvailableOnline(),
                    book.getBookTypes(),
                    book.getWordCount(),
                    book.getDescription(),
                    book.getAuthors().stream().map(Author::getAuthorApiKey).toList(),
                    book.getGenres());
        }));
        if(returnValue.isPresent()) {
            return returnValue.get();
        }
        throw new NoSuchElementException("Book not found");
    }
}
