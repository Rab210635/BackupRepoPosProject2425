package spengergasse.at.sj2425scherzerrabar.persistence;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spengergasse.at.sj2425scherzerrabar.domain.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest  {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void can_save() {
        var adresse = new Address("spengergasse 20","Vienna",1010);
        var email = new Email("mail@mail.com");
        List<Author> authors = List.of (new Author("Max","Mustermann", List.of(adresse),email,"test"));
        var genres = List.of(BookGenre.COMICS, BookGenre.HORROR);
        var book = new Book("name",new Date(Date.UTC(5,5,5,0,0,0)),true,200,genres, authors);
        var saved = bookRepository.save(book);
        assertNotNull(saved);
    }

}