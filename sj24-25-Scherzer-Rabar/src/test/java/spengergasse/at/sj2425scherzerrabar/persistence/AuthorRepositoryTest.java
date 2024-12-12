package spengergasse.at.sj2425scherzerrabar.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.domain.Address;
import spengergasse.at.sj2425scherzerrabar.domain.Author;
import spengergasse.at.sj2425scherzerrabar.domain.EmailAddress;
import spengergasse.at.sj2425scherzerrabar.domain.LibrarySubscription;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
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
}