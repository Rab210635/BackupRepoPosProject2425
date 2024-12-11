package spengergasse.at.sj2425scherzerrabar.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.LibrarySubscription;

@Repository
public interface LibrarySubscriptionRepository extends JpaRepository<LibrarySubscription, Long> {
}
