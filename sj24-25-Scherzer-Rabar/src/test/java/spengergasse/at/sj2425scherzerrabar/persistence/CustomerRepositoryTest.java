package spengergasse.at.sj2425scherzerrabar.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spengergasse.at.sj2425scherzerrabar.domain.Address;
import spengergasse.at.sj2425scherzerrabar.domain.Borrowing;
import spengergasse.at.sj2425scherzerrabar.domain.Customer;
import spengergasse.at.sj2425scherzerrabar.domain.EmailAddress;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository repository;
    @Test
    void can_save(){
        //arrange
        var adresse = new Address("spengergasse 20","Vienna",1010);
        var email = new EmailAddress("mail@mail.com");
        var customer = new Customer("Max","Mustermann", email,List.of(adresse));
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