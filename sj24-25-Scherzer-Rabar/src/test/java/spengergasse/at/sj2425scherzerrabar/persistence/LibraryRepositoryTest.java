package spengergasse.at.sj2425scherzerrabar.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.TestcontainersConfiguration;
import spengergasse.at.sj2425scherzerrabar.domain.BookInLibraries;
import spengergasse.at.sj2425scherzerrabar.domain.Library;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@Import(TestcontainersConfiguration.class)
class LibraryRepositoryTest {

    @Autowired
    private LibraryRepository libraryRepository;


    @Test
    void can_save() {
        var address = FixturesFactory.libraryAddress();
        var books = List.of(new BookInLibraries());
        Library library = FixturesFactory.thalia(address,books);

        var savedLibrary = libraryRepository.save(library);
        assertNotNull(savedLibrary);
    }

    @Test
    void default_constr(){
        Library defaultconstructed = new Library();
        assertNotNull(defaultconstructed);
    }
}