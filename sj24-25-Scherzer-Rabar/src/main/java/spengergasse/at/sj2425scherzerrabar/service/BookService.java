package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spengergasse.at.sj2425scherzerrabar.commands.BookCommand;
import spengergasse.at.sj2425scherzerrabar.domain.Author;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.persistence.AuthorRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Book createBook(BookCommand command) {
        List<Author> authors = command.authorIds().stream()
                .map(authorRepository::findById)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        if (authors.isEmpty()) {
            throw new IllegalArgumentException("Kein gültiger Autor gefunden");
        }

        LocalDate released = command.releaseDate();

        Book book = new Book(
                command.name(), released, command.availableOnline(), command.wordCount(), command.genre(), authors, command.types()
        );

        return bookRepository.save(book);
    }


}
