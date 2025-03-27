package spengergasse.at.sj2425scherzerrabar.persistence;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.*;
import spengergasse.at.sj2425scherzerrabar.dtos.CopyDto;

import spengergasse.at.sj2425scherzerrabar.domain.BookType;
import java.util.List;
import java.util.Optional;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {

    public Optional<Copy> findCopyByCopyApiKey(String apiKey);

    List<Copy> getCopiesByBook_BookApiKey(ApiKey bookBookApiKey);

    List<Copy> getCopiesByPublisher_PublisherApiKey(ApiKey publisherPublisherApiKey);

    List<Copy> getCopiesByBookType(@NotNull BookType bookType);

    List<Copy> getCopiesByInBranch_BranchApiKey(ApiKey branch);

    @Query("""
    select new spengergasse.at.sj2425scherzerrabar.dtos.CopyDto(
        c.copyApiKey.apiKey,c.publisher.publisherApiKey.apiKey, c.bookType, c.pageCount ,c.book.bookApiKey.apiKey, c.inBranch.branchApiKey.apiKey
    ) from Copy c where c.copyApiKey.apiKey=:apiKey
    """)
    public Optional<CopyDto> findProjectedByCopyApiKey(String copyApiKey);


    @Query("""
    select new spengergasse.at.sj2425scherzerrabar.dtos.CopyDto(
        c.copyApiKey.apiKey,c.publisher.publisherApiKey.apiKey, c.bookType, c.pageCount ,c.book.bookApiKey.apiKey, c.inBranch.branchApiKey.apiKey
    ) from Copy c
    """)
    public List<CopyDto> findAllProjected();



    @Query("""
    select new spengergasse.at.sj2425scherzerrabar.dtos.CopyDto(
        c.copyApiKey.apiKey,c.publisher.publisherApiKey.apiKey, c.bookType, c.pageCount ,c.book.bookApiKey.apiKey, c.inBranch.branchApiKey.apiKey
    ) from Copy c where c.book.bookApiKey = :bookApiKey
    """)
    List<CopyDto> findAllProjectedByBook_BookApiKey(String bookApiKey);


    @Query("""
    select new spengergasse.at.sj2425scherzerrabar.dtos.CopyDto(
        c.copyApiKey.apiKey,c.publisher.publisherApiKey.apiKey, c.bookType, c.pageCount ,c.book.bookApiKey.apiKey, c.inBranch.branchApiKey.apiKey
    ) from Copy c where c.publisher.publisherApiKey = :publisherApiKey
    """)
    List<CopyDto> findAllProjectedByPublisher_PublisherApiKey(String publisherApiKey);



    @Query("""
    select new spengergasse.at.sj2425scherzerrabar.dtos.CopyDto(
        c.copyApiKey.apiKey,c.publisher.publisherApiKey.apiKey, c.bookType, c.pageCount ,c.book.bookApiKey.apiKey, c.inBranch.branchApiKey.apiKey
    ) from Copy c where c.bookType = :bookType
    """)
    List<CopyDto> findAllProjectedByBookType(@NotNull BookType bookType);

    @Query("""
    select new spengergasse.at.sj2425scherzerrabar.dtos.CopyDto(
        c.copyApiKey.apiKey,c.publisher.publisherApiKey.apiKey, c.bookType, c.pageCount ,c.book.bookApiKey.apiKey, c.inBranch.branchApiKey.apiKey
    ) from Copy c where c.inBranch.branchApiKey.apiKey = :branchApiKey
    """)
    List<CopyDto> findAllProjectedByInBranch_BranchApiKey(String branchApiKey);

}
