package spengergasse.at.sj2425scherzerrabar.dtos;

import spengergasse.at.sj2425scherzerrabar.domain.Address;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.BookInLibraries;

import java.util.List;

public record LibraryDto(
        ApiKey apiKey,
        String name,
        Address headquarters,
        List<BookInLibraries> booksInLibraries
) {}