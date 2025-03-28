package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spengergasse.at.sj2425scherzerrabar.commands.LibraryCommand;
import spengergasse.at.sj2425scherzerrabar.domain.*;
import spengergasse.at.sj2425scherzerrabar.dtos.LibraryDto;
import spengergasse.at.sj2425scherzerrabar.persistence.BookRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.LibraryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;

    public LibraryService(LibraryRepository libraryRepository, BookRepository bookRepository) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public LibraryDto createLibrary(LibraryCommand command) {

        List<Book> books = new ArrayList<>();
        command.booksInLibraries().stream().map(
                (bookInLibrariesCommand -> {
                    books.add(bookRepository.findBookByBookApiKey(bookInLibrariesCommand.bookApiKey())
                            .orElseThrow(() -> new NoSuchElementException("Book not found")));
                    return true;
                })
        ).forEach(x -> {});


        Library library = new Library(
                command.name(),
                Address.addressFromString(command.headquarters()),
                command.booksInLibraries().stream()
                        .map(binlc -> {
                                    var book = books.stream().filter(b -> b.getBookApiKey().apiKey().equals(binlc.bookApiKey())).findFirst().get();
                                    return new BookInLibraries(book,binlc.borrowLengthDays());
                                }
                        ).toList()
        );
        library = libraryRepository.save(library);
        return LibraryDto.libraryDtoFromLibrary(library);
    }

    @Transactional
    public void deleteLibrary(String libraryApiKey) {
        Library library = libraryRepository.findLibraryByLibraryApiKey(libraryApiKey)
                .orElseThrow(() -> new NoSuchElementException("Library not found"));
        libraryRepository.delete(library);
    }

    @Transactional
    public LibraryDto updateLibrary(LibraryCommand command) {
        Library library = libraryRepository.findLibraryByLibraryApiKey(command.apiKey())
                .orElseThrow(() -> new NoSuchElementException("Library not found"));

        library.setName(command.name());
        library.setHeadquarters(Address.addressFromString(command.headquarters()));

        List<Book> books = new ArrayList<>();
        command.booksInLibraries().stream().map(
                (bookInLibrariesCommand -> {
                    books.add(bookRepository.findBookByBookApiKey(bookInLibrariesCommand.bookApiKey())
                            .orElseThrow(() -> new NoSuchElementException("Book not found")));
                    return true;
                })
        ).forEach(x -> {});
       library.setBooksInLibraries(command.booksInLibraries().stream()
               .map(binlc -> {
                   var book = books.stream().filter(b -> b.getBookApiKey().apiKey().equals(binlc.bookApiKey())).findFirst().get();
                           return new BookInLibraries(book,binlc.borrowLengthDays());
               }
               ).toList()
       );

        library = libraryRepository.save(library);
        return LibraryDto.libraryDtoFromLibrary(library);
    }

    public List<LibraryDto> getLibraries() {
        return libraryRepository.findAll().stream().map(LibraryDto::libraryDtoFromLibrary).toList();
    }

    public LibraryDto getLibrary(String libraryApiKey) {
        return libraryRepository.findLibraryByLibraryApiKey(libraryApiKey)
                .map(LibraryDto::libraryDtoFromLibrary)
                .orElseThrow(() -> new NoSuchElementException("Library not found"));
    }

    public LibraryDto getLibraryByName(String name) {
        return libraryRepository.findLibraryByName(name).map(LibraryDto::libraryDtoFromLibrary).orElseThrow(() -> new NoSuchElementException("Library not found"));
    }

}
