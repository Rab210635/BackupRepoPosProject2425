package spengergasse.at.sj2425scherzerrabar.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.domain.Borrowing;

import java.util.Optional;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    public Optional<Borrowing> findBorrowingByBorrowingApiKey(String apiKey);

}
