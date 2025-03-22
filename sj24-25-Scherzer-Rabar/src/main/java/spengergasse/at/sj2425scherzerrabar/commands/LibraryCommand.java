package spengergasse.at.sj2425scherzerrabar.commands;

import spengergasse.at.sj2425scherzerrabar.domain.Address;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.BookInLibraries;
import spengergasse.at.sj2425scherzerrabar.domain.EmailAddress;

import java.util.List;

public record LibraryCommand(
        ApiKey apiKey,
        String name,
        Address headquarters,
        List<BookInLibraries> booksInLibraries
) {}