package spengergasse.at.sj2425scherzerrabar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.commands.AuthorCommand;
import spengergasse.at.sj2425scherzerrabar.commands.BookCommand;
import spengergasse.at.sj2425scherzerrabar.domain.*;
import spengergasse.at.sj2425scherzerrabar.persistence.AuthorRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
               new ApiKey("authorApiKey"),"Aaron",List.of(new Address("12","Wien",12)),
               "Paron", "krabar",new EmailAddress("hoho@s.s")))).isInstanceOf(Address.AddressException.class);
    }

    @Test
    void can_create_author(){
        when(authorRepository.save(any(Author.class))).then(AdditionalAnswers.returnsFirstArg());

        var author = authorService.createAuthor( new AuthorCommand(
                new ApiKey("authorApiKey"),"Aaron",List.of(new Address("12","Wien",1212)),
                "Paron", "krabar",new EmailAddress("hoho@sasd.at")));
        assertThat(author).isNotNull();
    }

    @Test
    void can_delete_existing_author(){
        Author author = FixturesFactory.author();
        when(authorRepository.getAuthorsByPenname(any())).thenReturn(Optional.of(author));

        authorService.deleteAuthor(new ApiKey("validApiKey"));


        assertThat(authorRepository.getAuthorsByPenname("Aaron")).isNull();
    }

    @Test
    void cant_delete_not_existing_author(){
        ApiKey apiKey = new ApiKey("authorApiKey");
        assertThatThrownBy(()->authorService.deleteAuthor(apiKey)).isInstanceOf(NoSuchElementException.class);
    }
}