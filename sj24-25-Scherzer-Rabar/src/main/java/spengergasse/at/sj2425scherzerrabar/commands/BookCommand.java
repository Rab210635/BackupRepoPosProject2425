package spengergasse.at.sj2425scherzerrabar.commands;

import spengergasse.at.sj2425scherzerrabar.domain.*;

import java.time.LocalDate;
import java.util.List;

public record BookCommand(
        String apiKey, String name, LocalDate releaseDate,
        Boolean availableOnline, List<String> types, Integer wordCount,
        String description, List<String> authorIds, List<String> genre) {}


//was kommt in command und was in DTO
//valueobject, enum, embeddable, richtype
//DTO   keine Domains
//Command   auch so
