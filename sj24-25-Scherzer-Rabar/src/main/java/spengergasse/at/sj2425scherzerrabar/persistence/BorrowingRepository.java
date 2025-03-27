package spengergasse.at.sj2425scherzerrabar.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.domain.Borrowing;
import spengergasse.at.sj2425scherzerrabar.domain.Copy;
import spengergasse.at.sj2425scherzerrabar.domain.Customer;
import spengergasse.at.sj2425scherzerrabar.dtos.BorrowingDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    public Optional<Borrowing> findBorrowingByBorrowingApiKey(String apiKey);
    public List<Borrowing> findBorrowingsByCopiesContaining(Copy copy);
    public List<Borrowing> findBorrowingsByCustomer(Customer customer);


    @Query("""
        select b from Borrowing b
            where b.borrowingApiKey = :apiKey
    """)
    public Optional<BorrowingDto> getProjectedBorrowingByBorrowingApiKey (String apiKey);

    @Query("""
        select b from Borrowing b
    """)
    public List<BorrowingDto> findAllProjected();

    @Query("""
        select b from Borrowing b
            where b.customer.customerApiKey = :customerApiKey
    """)
    public List<BorrowingDto> getProjectedBorrowingsByCustomerByCustomer(String customerApiKey);

    @Query("""
        select b from Borrowing b
            where EXISTS (SELECT c FROM b.copies c WHERE c.copyApiKey = :copyApiKey)
    """)
    public List<BorrowingDto> getProjectedBorrowingsByCopiesContains(String copyApiKey);
}
