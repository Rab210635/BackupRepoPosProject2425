package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spengergasse.at.sj2425scherzerrabar.commands.BookCommand;
import spengergasse.at.sj2425scherzerrabar.domain.*;
import spengergasse.at.sj2425scherzerrabar.dtos.BookDto;
import spengergasse.at.sj2425scherzerrabar.dtos.BorrowingDto;
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
    public BookDto createBook(BookCommand command) {
        List<Author> authors = command.authorIds().stream()
                .map(authorRepository::findAuthorByAuthorApiKey)
                .flatMap(Optional::stream)
                .toList();
        if (authors.isEmpty()) {
            throw new NoSuchElementException("Author not found");
        }
        Book book = new Book(
                command.name(), command.releaseDate(), command.availableOnline(), command.wordCount(),
                command.genre().stream().map(BookGenre::valueOf).toList()
                , authors, command.types().stream().map(BookType::valueOf).toList(), command.description()
        );
        return BookDto.bookDtoFromBook( bookRepository.save(book));
    }

    @Transactional
    public void deleteBook(String bookApiKey) {
        Book book = bookRepository.findBookByBookApiKey(bookApiKey)
                .orElseThrow(NoSuchElementException::new);
        bookRepository.delete(book);
    }


    @Transactional
    public BookDto updateBook(BookCommand command) {
       Book book = bookRepository.findBookByBookApiKey(command.apiKey()).map((Book b)->{
            List<Author> authors = command.authorIds().stream()
                    .map(authorRepository::findAuthorByAuthorApiKey)
                    .flatMap(Optional::stream)
                    .toList();
            if(authors.isEmpty()){
                throw new NoSuchElementException("Author not found");
            }
            if(!b.getName().equals(command.name()))
                b.setName(command.name());
            if(b.getAvailableOnline() != command.availableOnline()) {
                b.setAvailableOnline(command.availableOnline());

            }
            if(!b.getDescription().equals(command.description()))
                b.setDescription(command.description());
            if(b.getReleaseDate() != command.releaseDate())
                b.setReleaseDate(command.releaseDate());

            b.setBookTypes(command.types().stream().map(BookType::valueOf).toList());
            b.setGenres(command.genre().stream().map(BookGenre::valueOf).toList());
            b.setAuthors(authors);
            //if(b.getAuthors())
            bookRepository.save(b);
            return b;
        }).orElseThrow(NoSuchElementException::new);
       return BookDto.bookDtoFromBook(book);
    }

    public List<BookDto> getBooks(String authorApiKey) {
        List<Book> books;
        if (authorApiKey != null) {
            Optional<Author> author = authorRepository.findAuthorByAuthorApiKey(authorApiKey);
            if(author.isPresent()) {
                books = bookRepository.findByAuthorsContains(author.get());
            }else {
                throw new NoSuchElementException("Author not found");
            }
        } else {
            books = bookRepository.findAll();
        }
        return books.stream().map(BookDto::bookDtoFromBook).toList();
    }

    public BookDto getBook(String bookApiKey) {
        Optional<Book> returnValue = bookRepository.findBookByBookApiKey(bookApiKey);
        if(returnValue.isPresent()) {
            return BookDto.bookDtoFromBook(returnValue.get());
        }
        throw new NoSuchElementException("Book not found");
    }

}
