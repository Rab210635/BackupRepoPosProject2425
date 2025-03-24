package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spengergasse.at.sj2425scherzerrabar.commands.PublisherCommand;
import spengergasse.at.sj2425scherzerrabar.domain.Address;
import spengergasse.at.sj2425scherzerrabar.domain.Publisher;
import spengergasse.at.sj2425scherzerrabar.dtos.PublisherDto;
import spengergasse.at.sj2425scherzerrabar.persistence.PublisherRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Transactional
    public PublisherDto createPublisher(PublisherCommand command) {
        Publisher publisher = new Publisher(command.name(), Address.addressFromString(command.address()));
        publisher = publisherRepository.save(publisher);
        return PublisherDto.publisherDtoFromPublisher(publisher);
    }

    @Transactional
    public void deletePublisherByApiKey(String apiKey) {
        Publisher publisher = publisherRepository.findPublisherByPublisherApiKey(apiKey)
                .orElseThrow(() -> new NoSuchElementException("Publisher not found"));
        publisherRepository.delete(publisher);
    }

    @Transactional
    public PublisherDto updatePublisherByApiKey(PublisherCommand command) {
        Publisher publisher = publisherRepository.findPublisherByPublisherApiKey(command.apiKey())
                .orElseThrow(() -> new NoSuchElementException("Publisher not found"));

        publisher.setName(command.name());
        publisher.setAddress(Address.addressFromString(command.address()));
        publisher = publisherRepository.save(publisher);

        return PublisherDto.publisherDtoFromPublisher(publisher);
    }

    public List<PublisherDto> getAllPublishers() {
        return publisherRepository.findAll().stream()
                .map(PublisherDto::publisherDtoFromPublisher)
                .collect(Collectors.toList());
    }

    public PublisherDto getPublisherByApiKey(String apiKey) {
        Publisher publisher = publisherRepository.findPublisherByPublisherApiKey(apiKey)
                .orElseThrow(() -> new NoSuchElementException("Publisher not found"));
        return PublisherDto.publisherDtoFromPublisher(publisher);
    }

    public PublisherDto getPublisherByName(String name) {
        Publisher publisher = publisherRepository.findPublisherByName(name)
                .orElseThrow(() -> new NoSuchElementException("Publisher not found"));
        return PublisherDto.publisherDtoFromPublisher(publisher);
    }

}
