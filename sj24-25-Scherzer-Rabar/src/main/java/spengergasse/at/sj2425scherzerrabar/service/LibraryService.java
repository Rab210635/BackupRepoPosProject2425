package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spengergasse.at.sj2425scherzerrabar.commands.LibraryCommand;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.Library;
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
        Library library = new Library(
                command.name(),
                command.headquarters(),
                command.booksInLibraries()
        );
        library = libraryRepository.save(library);
        return toDto(library);
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
        library.setHeadquarters(command.headquarters());
        library.setBooksInLibraries(command.booksInLibraries());

        library = libraryRepository.save(library);
        return toDto(library);
    }

    public List<LibraryDto> getLibraries() {
        return libraryRepository.findAll().stream().map(this::toDto).toList();
    }

    public LibraryDto getLibrary(ApiKey libraryApiKey) {
        return libraryRepository.findLibraryByLibraryApiKey(libraryApiKey.apiKey())
                .map(this::toDto)
                .orElseThrow(() -> new NoSuchElementException("Library not found"));
    }

    public LibraryDto getLibraryByName(String name) {
        return libraryRepository.findLibraryByName(name).map(this::toDto).orElseThrow(() -> new NoSuchElementException("Library not found"));
    }

    private LibraryDto toDto(Library library) {
        return new LibraryDto(
                library.getLibraryApiKey(),
                library.getName(),
                library.getHeadquarters(),
                library.getBooksInLibraries()
        );
    }
}
