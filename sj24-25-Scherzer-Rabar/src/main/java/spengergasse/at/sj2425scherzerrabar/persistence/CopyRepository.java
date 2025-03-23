package spengergasse.at.sj2425scherzerrabar.persistence;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {
    public Optional<Copy> findCopyByCopyApiKey(String apiKey);

    List<Copy> getCopiesByBook_BookApiKey(ApiKey bookBookApiKey);

    List<Copy> getCopiesByPublisher_PublisherApiKey(ApiKey publisherPublisherApiKey);

    List<Copy> getCopiesByBookType(@NotNull BookType bookType);
}
