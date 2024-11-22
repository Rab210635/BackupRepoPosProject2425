package spengergasse.at.sj2425scherzerrabar.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.BuyableBook;
import spengergasse.at.sj2425scherzerrabar.domain.Order;

@Repository
public interface BuyableBookRepository extends JpaRepository<BuyableBook, Long> {
}
