package spengergasse.at.sj2425scherzerrabar.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.Borrowing;
import spengergasse.at.sj2425scherzerrabar.domain.Copy;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {
}
