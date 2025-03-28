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
class OrderRepositoryTest {
    @Autowired
    private OrderRepository repository;
    @Test
    void can_save(){
        //arrange
        var order = FixturesFactory.order();
        //act
        var saved = repository.save(order);
        //assert
        assertNotNull(saved);
    }

    @Test
    void default_constr(){
        Order defaultconstructed = new Order();
        assertNotNull(defaultconstructed);
    }
}