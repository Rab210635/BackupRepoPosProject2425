package spengergasse.at.sj2425scherzerrabar.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spengergasse.at.sj2425scherzerrabar.domain.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository repository;
    @Test
    void can_save(){
        //arrange
        var adresse = new Address("spengergasse 20","Vienna",1010);
        var email = new EmailAddress("mail@mail.com");
        var customer = new Customer("Max","Mustermann", email,List.of(adresse));
        var author = new Author("Max","Mustermann",List.of(adresse), email, "dada");
        var book = new Book("dasd",new Date(Date.UTC(5,5,5,0,0,0)),true,1250,List.of(BookGenre.ROMANCE),List.of(author),List.of(BookType.EBOOK));
        var buyable = new BuyableBook(book, BookType.EBOOK,4.5f);
        // TODO branch & publisher adden
        var branch = new Branch();
        var publisher = new Publisher();
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