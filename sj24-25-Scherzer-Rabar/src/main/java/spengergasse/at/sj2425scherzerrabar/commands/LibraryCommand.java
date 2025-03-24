package spengergasse.at.sj2425scherzerrabar.commands;

import spengergasse.at.sj2425scherzerrabar.domain.Address;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.BookInLibraries;
import spengergasse.at.sj2425scherzerrabar.domain.EmailAddress;

import java.util.List;

public record LibraryCommand(
        String apiKey,
        String name,
        String headquarters,
        List<BookInLibrariesCommand> booksInLibraries
) {}