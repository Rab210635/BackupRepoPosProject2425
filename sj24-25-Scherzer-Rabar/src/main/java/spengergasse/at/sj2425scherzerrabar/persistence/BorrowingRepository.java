package spengergasse.at.sj2425scherzerrabar.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.domain.Borrowing;
import spengergasse.at.sj2425scherzerrabar.domain.Copy;
import spengergasse.at.sj2425scherzerrabar.domain.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    public Optional<Borrowing> findBorrowingByBorrowingApiKey(String apiKey);
    public List<Borrowing> findBorrowingsByCopiesContaining(Copy copy);
    public List<Borrowing> findBorrowingsByCustomer(Customer customer);

}
