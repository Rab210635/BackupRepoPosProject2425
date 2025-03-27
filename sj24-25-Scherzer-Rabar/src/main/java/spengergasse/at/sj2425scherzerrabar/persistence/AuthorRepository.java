package spengergasse.at.sj2425scherzerrabar.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.Author;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.dtos.AuthorDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    public Optional<Author> findAuthorByAuthorApiKey(String apiKey);


    Optional<Author> getAuthorsByPenname(String penname);

    Optional<Author> getAuthorByEmailAddress_Email(String emailAddressEmail);


    @Query(""" 
    SELECT a FROM Author a WHERE a.authorApiKey.apiKey = :apiKey
    """)
    public Optional<AuthorDto> getProjectedAuthorByAuthorApiKey(String apiKey);

    @Query("""
    select a from Author a
    """)
    public List<AuthorDto> findAllProjected();

    @Query("""
        select a from Author a where a.penname = :penname
    """)
    public Optional<AuthorDto> findAuthorByPenname(String penname);

    @Query("""
        select a from Author a where a.emailAddress = :emailAddressEmail
    """)
    public Optional<AuthorDto> findAuthorByEmailAddress_Email(String emailAddressEmail);
}
