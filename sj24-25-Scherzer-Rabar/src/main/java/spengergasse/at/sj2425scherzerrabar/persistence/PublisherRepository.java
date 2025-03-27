package spengergasse.at.sj2425scherzerrabar.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.domain.Publisher;
import spengergasse.at.sj2425scherzerrabar.dtos.PublisherDto;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    public Optional<Publisher> findPublisherByPublisherApiKey(String apiKey);

    public Optional<Publisher> findPublisherByName(String name);


    @Query("""
    select new spengergasse.at.sj2425scherzerrabar.dtos.PublisherDto(
        p.publisherApiKey, p.name, p.address
    ) from Publisher p where p.publisherApiKey =  :apiKey
    """)
    public Optional<PublisherDto> findProjectedByPublisherApiKey(String apiKey);

    @Query("""
    select new spengergasse.at.sj2425scherzerrabar.dtos.PublisherDto(
        p.publisherApiKey, p.name, p.address
    ) from Publisher p where p.name =  :name
    """)
    public Optional<PublisherDto> findProjectedByName(String name);


}
