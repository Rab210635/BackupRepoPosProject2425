package spengergasse.at.sj2425scherzerrabar.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.Author;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.dtos.BookDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    public Optional<Book> findBookByBookApiKey(String apiKey);

    public List<Book> findByAuthorsContains(Author author);

    @Query(""" 
    SELECT b FROM Book b WHERE b.bookApiKey.apiKey = :bookApiKey
    """)
    public Optional<BookDto> getProjectedBookByBookApiKey(String bookApiKey);

    @Query("""
    select b from Book b
    """)
    public List<BookDto> findAllProjected();

    @Query("""
    select b from Book b
            WHERE exists (select a from b.authors a where a.authorApiKey = :authorApiKey)
    """)
    public List<BookDto> findBooksByAuthorsContains(String authorApiKey);


}