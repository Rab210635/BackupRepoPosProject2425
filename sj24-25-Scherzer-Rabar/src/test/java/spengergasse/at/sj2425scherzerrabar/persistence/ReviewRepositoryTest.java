package spengergasse.at.sj2425scherzerrabar.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.domain.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository repository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    void can_save(){
        //arrange
        var adresse = FixturesFactory.libraryAddress();
        var email = new EmailAddress("mail@mail.com");
        var customer = new Customer("Max","Mustermann", email,List.of(adresse));
        var author = FixturesFactory.author(adresse,email);
        var book = FixturesFactory.book(author);
        var buyable = new BuyableBook(book, BookType.EBOOK,4.5f);
        // TODO branch & publisher adden

        var branch = FixturesFactory.filiale(adresse,List.of(FixturesFactory.libBook(book)));
        var publisher = FixturesFactory.dornbund(adresse);
        var review = new Review("dasds",5,"dasdsa",customer,book,branch,publisher);
        var review2 = new Review();
        //act
        var saved = repository.save(review);
        //assert
        assertNotNull(saved);
    }

    @Test
    void default_constr(){
        Review defaultconstructed = new Review();
        assertNotNull(defaultconstructed);
    }
}