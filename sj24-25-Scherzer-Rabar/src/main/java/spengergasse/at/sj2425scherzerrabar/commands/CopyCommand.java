package spengergasse.at.sj2425scherzerrabar.commands;

import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.domain.BookType;
import spengergasse.at.sj2425scherzerrabar.domain.Publisher;

public record CopyCommand(ApiKey apiKey, ApiKey publisherApiKey, BookType bookType, Integer pageCount, ApiKey bookApiKey) {
}
