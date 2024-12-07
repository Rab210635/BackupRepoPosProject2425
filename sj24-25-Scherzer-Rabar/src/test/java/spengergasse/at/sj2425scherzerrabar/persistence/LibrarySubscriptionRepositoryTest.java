package spengergasse.at.sj2425scherzerrabar.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.domain.BookInLibraries;
import spengergasse.at.sj2425scherzerrabar.domain.LibrarySubscription;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LibrarySubscriptionRepositoryTest {

    @Autowired
    private LibrarySubscriptionRepository librarySubscriptionRepository;

    @Test
    void can_save() {
        var libraryAddress = FixturesFactory.libraryAddress();
        var books = List.of(new BookInLibraries());
        var library = FixturesFactory.thalia(libraryAddress,books);
        LibrarySubscription librarySubscription = FixturesFactory.thaliaAll(library);

        var savedLibrarySubscription = librarySubscriptionRepository.save(librarySubscription);

        assertNotNull(savedLibrarySubscription);
    }
}