package spengergasse.at.sj2425scherzerrabar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.commands.AuthorCommand;
import spengergasse.at.sj2425scherzerrabar.domain.*;
import spengergasse.at.sj2425scherzerrabar.dtos.AuthorDto;
import spengergasse.at.sj2425scherzerrabar.persistence.AuthorRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    private @Mock AuthorRepository authorRepository;

    private AuthorService authorService;

    @BeforeEach
    void setUp(){
        assumeThat(authorRepository).isNotNull();
        authorService = new AuthorService(authorRepository);
    }

    @Test
    void cant_create_author_with_wrong_address() {
       assertThatThrownBy(()-> authorService.createAuthor( new AuthorCommand(
               new ApiKey("authorApiKey").apiKey(),"Aaron",List.of(new Address("12","Wien",12).toString()),
               "Paron", "krabar",new EmailAddress("hoho@s.s").email()))).isInstanceOf(Address.AddressException.class);
    }

    @Test
    void can_create_author(){
        when(authorRepository.save(any(Author.class))).then(AdditionalAnswers.returnsFirstArg());

        var author = authorService.createAuthor( new AuthorCommand(
                new ApiKey("authorApiKey").apiKey(),"Aaron",List.of(new Address("12","Wien",1212).toString()),
                "Paron", "krabar",new EmailAddress("hoho@sasd.at").email()));
        assertThat(author).isNotNull();
    }

    @Test
    void can_delete_existing_author(){
        Author author = FixturesFactory.author();
        when(authorRepository.findAuthorByAuthorApiKey(any())).thenReturn(Optional.of(author));

        authorService.deleteAuthor(new ApiKey("validApiKey").apiKey());

        verify(authorRepository, times(1)).delete(author);
    }

    @Test
    void cant_delete_not_existing_author(){
        ApiKey apiKey = new ApiKey("authorApiKey");
        assertThatThrownBy(()->authorService.deleteAuthor(apiKey.apiKey())).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void can_update_existing_author(){
        Author author = FixturesFactory.author();
        when(authorRepository.findAuthorByAuthorApiKey(any())).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).then(AdditionalAnswers.returnsFirstArg());

        authorService.updateAuthor(new AuthorCommand(
                new ApiKey("authorApiKey").apiKey(),"Update",List.of(new Address("12","Wien",1212).toString()),
                "Mustermann", "Max",new EmailAddress("hoho@sasd.at").email()));

        verify(authorRepository, times(1)).save(author);
        assertThat(author.getPenname()).isEqualTo("Update");
        assertThat(author.getLastName()).isEqualTo("Max");
        assertThat(author.getFirstName()).isEqualTo("Mustermann");
    }

    @Test
    void cant_update_not_existing_author(){
        assertThatThrownBy(()->authorService.updateAuthor(new AuthorCommand(
                new ApiKey("authorApiKey").apiKey(),"Update",List.of(new Address("12","Wien",1212).toString()),
                "Mustermann", "Max",new EmailAddress("hoho@sasd.at").email())))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void can_get_existing_author_by_id(){
        Author author = FixturesFactory.author();
        when(authorRepository.findAuthorByAuthorApiKey(any())).thenReturn(Optional.of(author));
        var author1 = authorService.getAuthor(author.getAuthorApiKey().apiKey());
        assertThat(author1).isEqualTo(AuthorDto.authorDtoFromAuthor(author));
        verify(authorRepository, times(1)).findAuthorByAuthorApiKey(any());
    }

    @Test
    void cant_get_not_existing_author_by_id(){
        assertThatThrownBy(()->authorService.getAuthor(new ApiKey("authorApiKey").apiKey())).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void can_get_existing_author_by_penname(){
        Author author = FixturesFactory.author();
        when(authorRepository.getAuthorsByPenname(any())).thenReturn(Optional.of(author));
        var author1 = authorService.getAuthorByPenname(author.getPenname());
        assertThat(author1).isNotNull();
        verify(authorRepository, times(1)).getAuthorsByPenname(any());
    }

    @Test
    void cant_get_not_existing_author_by_penname(){
        assertThatThrownBy(()->authorService.getAuthorByPenname("A")).isInstanceOf(NoSuchElementException.class).hasMessageContaining("Author not found");
    }

    @Test
    void can_get_authors() {
        Author author = FixturesFactory.author();
        Author author2 = FixturesFactory.author();
        when(authorRepository.findAll()).thenReturn(List.of(author,author2));

        var authors = authorService.getAuthors();
        assertThat(authors).hasSize(2);
    }

    @Test
    void can_get_existing_author_by_email_address(){
        Author author = FixturesFactory.author();
        when(authorRepository.getAuthorByEmailAddress_Email(any())).thenReturn(Optional.of(author));
        var author1 = authorService.getAuthorByEmailAddress(author.getEmailAddress().email());
        assertThat(author1).isNotNull();
        verify(authorRepository, times(1)).getAuthorByEmailAddress_Email(any());
    }

    @Test
    void cant_get_not_existing_author_by_email_address(){
        assertThatThrownBy(()->authorService.getAuthorByEmailAddress("A")).isInstanceOf(NoSuchElementException.class);
    }
}