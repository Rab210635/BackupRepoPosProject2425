package spengergasse.at.sj2425scherzerrabar.dtos;

import spengergasse.at.sj2425scherzerrabar.domain.*;

import java.time.LocalDate;
import java.util.List;

public record BookDto(String apiKey, String name, LocalDate releaseDate,
                      Boolean availableOnline, List<String> types, Integer wordCount,
                      String description, List<String> authorIds, List<String> genres)
{
    public static BookDto bookDtoFromBook(Book book) {
        return new BookDto(
          book.getBookApiKey().apiKey(),book.getName(),book.getReleaseDate(),book.getAvailableOnline(),
                book.getBookTypes().stream().map(Enum::name).toList(), book.getWordCount(),book.getDescription(),
                book.getAuthors().stream().map(author -> author.getAuthorApiKey().apiKey()).toList(),
                book.getGenres().stream().map(Enum::name).toList()
        );
    }
}
