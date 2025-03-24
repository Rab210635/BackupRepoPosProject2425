package spengergasse.at.sj2425scherzerrabar.dtos;


import spengergasse.at.sj2425scherzerrabar.domain.Publisher;

public record PublisherDto(String apiKey, String name, String address) {

    public static PublisherDto publisherDtoFromPublisher(Publisher publisher) {
        return new PublisherDto(
                publisher.getPublisherApiKey().apiKey(),publisher.getName(),publisher.getAddress().toString()
        );
    }
}
