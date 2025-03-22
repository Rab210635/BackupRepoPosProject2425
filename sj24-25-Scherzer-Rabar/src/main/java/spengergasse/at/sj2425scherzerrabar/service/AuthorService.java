package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import spengergasse.at.sj2425scherzerrabar.commands.AuthorCommand;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.Author;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.persistence.AuthorRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.BookRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.converter.BookGenreConverter;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly=true)
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Author createAuthor(AuthorCommand command) {

        Author author = new Author(
                command.firstname(), command.lastname(), command.address(),command.emailAddress(),command.penname()
        );
        return authorRepository.save(author);
    }

    @Transactional
    public void deleteAuthor(ApiKey apiKey) {
       Author author = authorRepository.findAuthorByAuthorApiKey(apiKey.apiKey())
               .orElseThrow(NoSuchElementException::new);
       authorRepository.delete(author);
    }

    @Transactional
    public void updateAuthor(AuthorCommand command) {
        authorRepository.findAuthorByAuthorApiKey(command.apiKey().apiKey()).map((Author a)->{
            if(!a.getPenname().equals(command.penname()))
                a.setPenname(command.penname());
            if(!a.getFirstName().equals(command.firstname()))
                a.setFirstName(command.firstname());
            if(!a.getLastName().equals(command.lastname()))
                a.setLastName(command.lastname());
            if(!a.getEmailAddress().email().equals(command.emailAddress().email()))
                a.setEmailAddress(command.emailAddress());
            if(a.getAddress() != command.address())
                a.setAddress(command.address());

            authorRepository.save(a);
            return a;
        }).orElseThrow(NoSuchElementException::new);
    }

    public static class AuthorException extends RuntimeException {
        public AuthorException(String message) {
            super(message);
        }
        public static AuthorException withInvalidDatabaseValue(String value){
            String message = "The value provided is not valid: (%s)".formatted(value);
            return new AuthorException(message);
        }
    }
}




