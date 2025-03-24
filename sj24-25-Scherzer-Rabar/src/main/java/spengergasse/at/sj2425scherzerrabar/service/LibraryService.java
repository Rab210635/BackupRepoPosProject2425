package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spengergasse.at.sj2425scherzerrabar.commands.LibraryCommand;
import spengergasse.at.sj2425scherzerrabar.domain.*;
import spengergasse.at.sj2425scherzerrabar.dtos.LibraryDto;
import spengergasse.at.sj2425scherzerrabar.persistence.LibraryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LibraryService {
    private final LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    @Transactional
    public LibraryDto createLibrary(LibraryCommand command) {
        Book book = new Book(); //TODO
        Library library = new Library(
                command.name(),
                Address.addressFromString(command.headquarters()),
                command.booksInLibraries().stream()
                        .map(binlc -> new BookInLibraries(book,binlc.borrowLengthDays())).toList()
        );
        library = libraryRepository.save(library);
        return LibraryDto.libraryDtoFromLibrary(library);
    }

    @Transactional
    public void deleteLibrary(ApiKey libraryApiKey) {
        Library library = libraryRepository.findLibraryByLibraryApiKey(libraryApiKey.apiKey())
                .orElseThrow(() -> new NoSuchElementException("Library not found"));
        libraryRepository.delete(library);
    }

    @Transactional
    public LibraryDto updateLibrary(ApiKey libraryApiKey, LibraryCommand command) {
        Library library = libraryRepository.findLibraryByLibraryApiKey(libraryApiKey.apiKey())
                .orElseThrow(() -> new NoSuchElementException("Library not found"));

        library.setName(command.name());
        library.setHeadquarters(Address.addressFromString(command.headquarters()));
       // library.setBooksInLibraries(command.booksInLibraries()); //TODO

        library = libraryRepository.save(library);
        return LibraryDto.libraryDtoFromLibrary(library);
    }

    public List<LibraryDto> getLibraries() {
        return libraryRepository.findAll().stream().map(LibraryDto::libraryDtoFromLibrary).toList();
    }

    public LibraryDto getLibrary(ApiKey libraryApiKey) {
        return libraryRepository.findLibraryByLibraryApiKey(libraryApiKey.apiKey())
                .map(LibraryDto::libraryDtoFromLibrary)
                .orElseThrow(() -> new NoSuchElementException("Library not found"));
    }

    public LibraryDto getLibraryByName(String name) {
        return libraryRepository.findLibraryByName(name).map(LibraryDto::libraryDtoFromLibrary).orElseThrow(() -> new NoSuchElementException("Library not found"));
    }

}
