package spengergasse.at.sj2425scherzerrabar.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.TestcontainersConfiguration;
import spengergasse.at.sj2425scherzerrabar.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository repository;
    @Test
    void can_save(){
        //arrange
        var review = FixturesFactory.review();
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