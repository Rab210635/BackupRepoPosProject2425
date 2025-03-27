package spengergasse.at.sj2425scherzerrabar.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.domain.Branch;
import spengergasse.at.sj2425scherzerrabar.domain.Library;
import spengergasse.at.sj2425scherzerrabar.dtos.BranchDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch,Long> {
    public Optional<Branch> findBranchByBranchApiKey(String apiKey);
    public List<Branch> findBranchesByLibrary(Library book);

    @Query("""
        select b from Branch b
            where b.branchApiKey = :branchApiKey
    """)
    public Optional<BranchDto> getProjectedBranchByBranchApiKey(String branchApiKey);

    @Query("""
        select b from Branch b
    """)
    public List<BranchDto> findAllProjected();

    @Query("""
    select b from Branch b
        where exists (select l from b.library l where l.libraryApiKey = :libraryApiKey)
    """)
    public List<BranchDto> getProjectedBranchesByLibrary(String libraryApiKey);
}
