package spengergasse.at.sj2425scherzerrabar.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BranchRepositoryTest {
    @Autowired
    private BranchRepository branchRepository;

    @Test
    void can_save() {
        var libraryAddress = FixturesFactory.libraryAddress();
        var books = List.of(new BookInLibraries());
        var library = FixturesFactory.thalia(libraryAddress,books);
        var address2 = FixturesFactory.address2();
        var copys = List.of(new Copy());
        Branch branch = new Branch(library,address2,copys);

        var savedBranch = branchRepository.save(branch);

        assertNotNull(savedBranch);
    }
}