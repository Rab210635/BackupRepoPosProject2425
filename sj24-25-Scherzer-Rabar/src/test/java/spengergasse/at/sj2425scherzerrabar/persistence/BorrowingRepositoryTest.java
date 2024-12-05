package spengergasse.at.sj2425scherzerrabar.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spengergasse.at.sj2425scherzerrabar.domain.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BorrowingRepositoryTest {
    @Autowired
    private BorrowingRepository repository;
    @Test
    void can_save(){
        //arrange
        var adresse = new Address("spengergasse 20","Vienna",1010);
        var email = new EmailAddress("mail@mail.com");
        var author = new Author("Max","Mustermann",List.of(adresse), email, "dada");
        var book = new Book("dasd",new Date(Date.UTC(5,5,5,0,0,0)),true,1250,List.of(BookGenre.ROMANCE),List.of(author),List.of(BookType.EBOOK));
        var publisher = new Publisher();
        var customer = new Customer("Max","Mustermann", email,List.of(adresse));
        var copy = new Copy(publisher,BookType.PAPERBACK,244,book);
        var borrowing = new Borrowing(customer,List.of(copy),new Date(Date.UTC(5,5,5,0,0,0)),25);
        var borrowing2 = new Borrowing();
        //act
        var saved = repository.save(borrowing);
        //assert
        assertNotNull(saved);
    }
}