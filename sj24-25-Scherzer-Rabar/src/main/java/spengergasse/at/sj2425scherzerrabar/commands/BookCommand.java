package spengergasse.at.sj2425scherzerrabar.commands;

import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.BookGenre;
import spengergasse.at.sj2425scherzerrabar.domain.BookType;

import java.time.LocalDate;
import java.util.List;

public record BookCommand(ApiKey apiKey, String name, LocalDate releaseDate, Boolean availableOnline, List<BookType> types, Integer wordCount, String description, List<ApiKey> authorIds, List<BookGenre> genre) {}
