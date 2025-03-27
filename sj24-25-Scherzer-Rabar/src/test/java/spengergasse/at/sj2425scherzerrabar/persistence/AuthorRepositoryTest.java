package spengergasse.at.sj2425scherzerrabar.persistence;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.TestcontainersConfiguration;
import spengergasse.at.sj2425scherzerrabar.domain.Address;
import spengergasse.at.sj2425scherzerrabar.domain.Author;
import static org.assertj.core.api.Assertions.assertThat;
import spengergasse.at.sj2425scherzerrabar.domain.EmailAddress;
import spengergasse.at.sj2425scherzerrabar.domain.LibrarySubscription;
import spengergasse.at.sj2425scherzerrabar.dtos.AuthorDto;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;
    @Test
    void can_save(){
        //arrange
        var author = FixturesFactory.author();
        //act
        var saved = repository.save(author);
        //assert
        assertNotNull(saved);
    }

    @Test
    void default_constr(){
        Author defaultconstructed = new Author();
        assertNotNull(defaultconstructed);
    }

    @Test
    void can_get_projection() {
        var author = FixturesFactory.author();
        when(repository.getProjectedAuthorByAuthorApiKey(any())).thenReturn(Optional.of(AuthorDto.authorDtoFromAuthor(author)));
        var autherBack = repository.getProjectedAuthorByAuthorApiKey(author.getAuthorApiKey().apiKey());

        assertThat(autherBack).isEqualTo(AuthorDto.authorDtoFromAuthor(author));
    }
}