package spengergasse.at.sj2425scherzerrabar.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.TestcontainersConfiguration;
import spengergasse.at.sj2425scherzerrabar.domain.Borrowing;
import spengergasse.at.sj2425scherzerrabar.domain.Publisher;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
class PublisherRepositoryTest {

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    void can_save() {
        var address = FixturesFactory.address2();
        Publisher publisher = FixturesFactory.dornbund(address);

        var savedPublisher = publisherRepository.save(publisher);

        assertNotNull(savedPublisher);
    }
    @Test
    void default_constr(){
        Publisher defaultconstructed = new Publisher();
        assertNotNull(defaultconstructed);
    }

}