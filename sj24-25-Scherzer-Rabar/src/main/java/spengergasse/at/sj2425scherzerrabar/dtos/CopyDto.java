package spengergasse.at.sj2425scherzerrabar.dtos;

import spengergasse.at.sj2425scherzerrabar.domain.*;

public record CopyDto(String apiKey, String publisherApiKey, String bookType, Integer pageCount, String bookApiKey) {
    public static CopyDto copyDtoFromCopy(Copy copy) {
        return new CopyDto(copy.getCopyApiKey().apiKey(), copy.getPublisher().getPublisherApiKey().apiKey(), copy.getBookType().name(), copy.getPageCount(), copy.getBook().getBookApiKey().apiKey());
    }
}
