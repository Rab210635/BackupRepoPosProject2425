package spengergasse.at.sj2425scherzerrabar.persistence;

import org.assertj.core.api.SoftAssertions;
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
class CopyRepositoryTest {
    @Autowired
    private CopyRepository repository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void can_save(){
        var copy = FixturesFactory.copy();

        var saved = repository.save(copy);

        assertNotNull(saved);
    }

    @Test
    void default_constr(){
        Copy defaultconstructed = new Copy();
        assertNotNull(defaultconstructed);
    }


    @Test
    void can_find_projected(){
        var copy = FixturesFactory.copy();
        copy.getBook().getAuthors().get(0).setPenname("Andere");
        copy.getBook().getAuthors().get(0).setEmailAddress(new EmailAddress("andereMail@gmail.com"));
        repository.saveAndFlush(copy);
        var found = repository.findProjectedByCopyApiKey(copy.getCopyApiKey().apiKey());

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(found).isNotNull();
        softly.assertThat(found).isNotEmpty();
        softly.assertThat(found.get().bookApiKey()).isEqualTo(copy.getBook().getBookApiKey().apiKey());
        softly.assertAll();
    }

}