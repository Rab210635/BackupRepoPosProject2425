package spengergasse.at.sj2425scherzerrabar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.commands.LibraryCommand;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.BookInLibraries;
import spengergasse.at.sj2425scherzerrabar.domain.Library;
import spengergasse.at.sj2425scherzerrabar.dtos.LibraryDto;
import spengergasse.at.sj2425scherzerrabar.persistence.LibraryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {
    private @Mock LibraryRepository libraryRepository;

    private LibraryService libraryService;

    @BeforeEach
    void setUp() {
        libraryService = new LibraryService(libraryRepository);
    }



    @Test
    void can_create_library() {
        BookInLibraries bookInLibraries = FixturesFactory.libBook(FixturesFactory.book(FixturesFactory.author()));
        var command = new LibraryCommand(new ApiKey("LibraryKey"), "Thalia", FixturesFactory.libraryAddress(),List.of(bookInLibraries));
        when(libraryRepository.save(any(Library.class))).then(AdditionalAnswers.returnsFirstArg());

        LibraryDto createdLibrary = libraryService.createLibrary(command);

        assertThat(createdLibrary).isNotNull();
        assertThat(createdLibrary.name()).isEqualTo("Thalia");
        assertThat(createdLibrary.headquarters()).isEqualTo(FixturesFactory.libraryAddress());
        System.out.println("LOOK: "+createdLibrary.booksInLibraries().getFirst().getBook().getName());
        assertThat(createdLibrary.booksInLibraries()).contains(bookInLibraries);
    }

    @Test
    void cant_delete_library_with_missing_library() {
        when(libraryRepository.findLibraryByLibraryApiKey(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> libraryService.deleteLibrary(new ApiKey("invalidLibraryApiKey")))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Library not found");
    }

    @Test
    void can_delete_library() {
        Library library = FixturesFactory.thalia(FixturesFactory.libraryAddress(),List.of(FixturesFactory.libBook(FixturesFactory.book(FixturesFactory.author()))));
        when(libraryRepository.findLibraryByLibraryApiKey(any())).thenReturn(Optional.of(library));

        libraryService.deleteLibrary(library.getLibraryApiKey());

        verify(libraryRepository, times(1)).delete(library);
    }

    @Test
    void cant_update_library_with_missing_library() {
        when(libraryRepository.findLibraryByLibraryApiKey(any())).thenReturn(Optional.empty());

        var command = new LibraryCommand(new ApiKey("LibraryKey"), "Thalia", FixturesFactory.libraryAddress(),List.of(FixturesFactory.libBook(FixturesFactory.book(FixturesFactory.author()))));

        assertThatThrownBy(() -> libraryService.updateLibrary(new ApiKey("invalidLibraryApiKey"), command))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Library not found");
    }

    @Test
    void can_update_library() {
        Library library = FixturesFactory.thalia(FixturesFactory.libraryAddress(),List.of(FixturesFactory.libBook(FixturesFactory.book(FixturesFactory.author()))));
        var command = new LibraryCommand(new ApiKey("LibraryKey"), "UpdatedLibrary", FixturesFactory.libraryAddress(),List.of(FixturesFactory.libBook(FixturesFactory.book(FixturesFactory.author()))));
        when(libraryRepository.findLibraryByLibraryApiKey(any())).thenReturn(Optional.of(library));
        when(libraryRepository.save(any(Library.class))).then(AdditionalAnswers.returnsFirstArg());

        LibraryDto updatedLibrary = libraryService.updateLibrary(library.getLibraryApiKey(), command);

        assertThat(updatedLibrary).isNotNull();
        assertThat(updatedLibrary.name()).isEqualTo("UpdatedLibrary");
    }

    @Test
    void can_get_all_libraries() {
        Library library1 = FixturesFactory.thalia(FixturesFactory.libraryAddress(),List.of(FixturesFactory.libBook(FixturesFactory.book(FixturesFactory.author()))));
        Library library2 = FixturesFactory.thalia(FixturesFactory.address2(),List.of(FixturesFactory.libBook(FixturesFactory.book(FixturesFactory.author()))));
        when(libraryRepository.findAll()).thenReturn(List.of(library1, library2));

        List<LibraryDto> libraries = libraryService.getLibraries();

        assertThat(libraries).hasSize(2);
    }

    @Test
    void cant_get_library_with_missing_library() {
        when(libraryRepository.findLibraryByLibraryApiKey(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> libraryService.getLibrary(new ApiKey("invalidLibraryApiKey")))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Library not found");
    }

    @Test
    void can_get_library() {
        Library library = FixturesFactory.thalia(FixturesFactory.libraryAddress(),List.of(FixturesFactory.libBook(FixturesFactory.book(FixturesFactory.author()))));
        when(libraryRepository.findLibraryByLibraryApiKey(any())).thenReturn(Optional.of(library));

        LibraryDto libraryDto = libraryService.getLibrary(library.getLibraryApiKey());

        assertThat(libraryDto).isNotNull();
        assertThat(libraryDto.name()).isEqualTo(library.getName());
    }

    @Test
    void cant_get_library_by_name_with_missing_library() {
        when(libraryRepository.findLibraryByName(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> libraryService.getLibraryByName("NonExistingLibrary"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Library not found");
    }

    @Test
    void can_get_library_by_name() {
        Library library = FixturesFactory.thalia(FixturesFactory.libraryAddress(),List.of(FixturesFactory.libBook(FixturesFactory.book(FixturesFactory.author()))));
        when(libraryRepository.findLibraryByName(any())).thenReturn(Optional.of(library));

        LibraryDto libraryDto = libraryService.getLibraryByName("Thalia");

        assertThat(libraryDto).isNotNull();
        assertThat(libraryDto.name()).isEqualTo(library.getName());
    }
}
