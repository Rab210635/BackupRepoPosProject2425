package spengergasse.at.sj2425scherzerrabar.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import spengergasse.at.sj2425scherzerrabar.TestcontainersConfiguration;
import spengergasse.at.sj2425scherzerrabar.domain.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
class BorrowingRepositoryTest {
    @Autowired
    private BorrowingRepository repository;
    @Test
    void can_save(){
        //arrange
        var adresse = new Address("spengergasse 20","Vienna",1010);
        var email = new EmailAddress("mail@mail.com");
        var author = new Author("Max","Mustermann",List.of(adresse), email, "dada");
        var book = new Book("dasd", LocalDate.of(2000,5,5),true,1250,List.of(BookGenre.ROMANCE),List.of(author),List.of(BookType.EBOOK));
        var publisher = new Publisher();
        var customer = new Customer("Max","Mustermann", email,List.of(adresse));
        var copy = new Copy(publisher,BookType.PAPERBACK,244,book);
        var borrowing = new Borrowing(customer,List.of(copy),LocalDate.of(2024,5,5),25);
        //act
        var saved = repository.save(borrowing);
        //assert
        assertNotNull(saved);
    }

    @Test
    void default_constr(){
        Borrowing defaultconstructed = new Borrowing();
        assertNotNull(defaultconstructed);
    }
}