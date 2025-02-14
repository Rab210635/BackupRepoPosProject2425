package spengergasse.at.sj2425scherzerrabar.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.TestcontainersConfiguration;
import spengergasse.at.sj2425scherzerrabar.domain.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
class CopyRepositoryTest {
    @Autowired
    private CopyRepository repository;
    @Test
    void can_save(){
        var copy = FixturesFactory.copy();

        var saved = repository.save(copy);

        assertNotNull(saved);
    }

    @Test
    void default_constr(){
        Copy defaultconstructed = new Copy();
        assertNotNull(defaultconstructed);
    }
}