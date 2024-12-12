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
class BuyableBookRepositoryTest {
    @Autowired
    private BuyableBookRepository repository;
    @Autowired
    private BookRepository bookRepository;
    @Test
    void can_save(){
        //arrange
        var buyable = FixturesFactory.buyableBook();
        //act
        var saved = repository.save(buyable);
        //assert
        assertNotNull(saved);
    }

    @Test
    void default_constr(){
        BuyableBook defaultconstructed = new BuyableBook();
        assertNotNull(defaultconstructed);
    }
}