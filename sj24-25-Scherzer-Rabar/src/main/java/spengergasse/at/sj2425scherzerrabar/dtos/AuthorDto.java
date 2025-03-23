package spengergasse.at.sj2425scherzerrabar.dtos;

import spengergasse.at.sj2425scherzerrabar.domain.Address;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.Author;
import spengergasse.at.sj2425scherzerrabar.domain.EmailAddress;

import java.util.List;

public record AuthorDto(
        ApiKey apiKey,
        String penname,
        List<Address> address,
        String firstname,
        String lastname,
        EmailAddress emailAddress
) {
    public static AuthorDto authorDtoFromAuthor(Author author) {
        return new AuthorDto(
                author.getAuthorApiKey(), author.getPenname(), author.getAddress(),author.getFirstName(), author.getLastName(), author.getEmailAddress()
        );
    }
}
