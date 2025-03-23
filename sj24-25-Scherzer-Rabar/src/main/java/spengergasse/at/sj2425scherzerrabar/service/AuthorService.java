package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import spengergasse.at.sj2425scherzerrabar.commands.AuthorCommand;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.Author;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.dtos.AuthorDto;
import spengergasse.at.sj2425scherzerrabar.dtos.BookDto;
import spengergasse.at.sj2425scherzerrabar.persistence.AuthorRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.BookRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.converter.BookGenreConverter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly=true)
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public AuthorDto createAuthor(AuthorCommand command) {

        Author author = new Author(
                command.firstname(), command.lastname(), command.address(),command.emailAddress(),command.penname()
        );
         authorRepository.save(author);
        return AuthorDto.authorDtoFromAuthor(author);
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
            if(!a.getAddress().equals(command.address()))
                a.setAddress(command.address());

            authorRepository.save(a);
            return a;
        }).orElseThrow(NoSuchElementException::new);
    }


    public AuthorDto getAuthor(ApiKey apiKey) {
        return authorRepository.findAuthorByAuthorApiKey(apiKey.apiKey()).map(AuthorDto::authorDtoFromAuthor).orElseThrow(NoSuchElementException::new);
    }

    public List<AuthorDto> getAuthors() {
        List<Author> authors = authorRepository.findAll();

        return authors.stream().map(AuthorDto::authorDtoFromAuthor).collect(Collectors.toList());
    }

    public AuthorDto getAuthorByPenname(String penname) {

        var a = authorRepository.getAuthorsByPenname(penname);
        if (a.isEmpty()) {
            throw new NoSuchElementException("Author not found");
        }
        return AuthorDto.authorDtoFromAuthor(a.get());
    }

    public AuthorDto getAuthorByEmailAddress(String emailAddress) {
        return authorRepository.getAuthorByEmailAddress_Email(emailAddress)
                .map(AuthorDto::authorDtoFromAuthor)
                .orElseThrow(NoSuchElementException::new);
    }
}




