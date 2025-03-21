package spengergasse.at.sj2425scherzerrabar.commands;

import spengergasse.at.sj2425scherzerrabar.domain.BookGenre;
import spengergasse.at.sj2425scherzerrabar.domain.BookType;

import java.time.LocalDate;
import java.util.List;

public record BookCommand(String name, LocalDate releaseDate, Boolean availableOnline, List<BookType> types, Integer wordCount, String description, List<Long> authorIds, List<BookGenre> genre) {}
