package spengergasse.at.sj2425scherzerrabar.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.TestcontainersConfiguration;
import spengergasse.at.sj2425scherzerrabar.domain.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
class BookRepositoryTest  {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void can_save() {
        var book = FixturesFactory.book(FixturesFactory.author());

        var saved = bookRepository.save(book);

        assertNotNull(saved);
    }
    @Test
    void default_constr(){
        Book defaultconstructed = new Book();
        assertNotNull(defaultconstructed);
    }

}