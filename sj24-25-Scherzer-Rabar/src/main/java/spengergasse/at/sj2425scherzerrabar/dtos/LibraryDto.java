package spengergasse.at.sj2425scherzerrabar.dtos;

import spengergasse.at.sj2425scherzerrabar.domain.Library;

import java.util.List;

public record LibraryDto(
        String apiKey,
        String name,
        String headquarters,
        List<BookInLibrariesDto> booksInLibraries
) {
    public static LibraryDto libraryDtoFromLibrary(Library library) {
        return new LibraryDto(
          library.getLibraryApiKey().apiKey(), library.getName(), library.getHeadquarters().toString(),
          library.getBooksInLibraries().stream().map(BookInLibrariesDto::bookInLibrariesDtoFromBookInLibraries).toList()
        );
    }
}