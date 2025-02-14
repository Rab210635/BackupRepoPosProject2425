package spengergasse.at.sj2425scherzerrabar.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.TestcontainersConfiguration;
import spengergasse.at.sj2425scherzerrabar.domain.Address;
import spengergasse.at.sj2425scherzerrabar.domain.Borrowing;
import spengergasse.at.sj2425scherzerrabar.domain.Customer;
import spengergasse.at.sj2425scherzerrabar.domain.EmailAddress;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository repository;
    @Test
    void can_save(){
        //arrange
        var customer = FixturesFactory.customer();
        //act
        var saved = repository.save(customer);
        //assert
        assertNotNull(saved);
    }

    @Test
    void default_constr(){
        Customer defaultconstructed = new Customer();
        assertNotNull(defaultconstructed);
    }
}