package spengergasse.at.sj2425scherzerrabar.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.domain.Library;

import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Integer> {
    public Optional<Library> findLibraryByLibraryApiKey(String apiKey);

}
