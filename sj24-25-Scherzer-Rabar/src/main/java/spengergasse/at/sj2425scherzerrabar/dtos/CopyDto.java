package spengergasse.at.sj2425scherzerrabar.dtos;

import spengergasse.at.sj2425scherzerrabar.domain.*;

public record CopyDto(ApiKey apiKey, ApiKey publisherApiKey, BookType bookType, Integer pageCount, ApiKey bookApiKey) {
    public static CopyDto copyDtoFromCopy(Copy copy) {
        return new CopyDto(copy.getCopyApiKey(), copy.getPublisher().getPublisherApiKey(), copy.getBookType(), copy.getPageCount(), copy.getBook().getBookApiKey());
    }
}
