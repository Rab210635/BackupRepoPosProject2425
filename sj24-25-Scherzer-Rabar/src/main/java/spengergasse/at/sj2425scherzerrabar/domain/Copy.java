package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import spengergasse.at.sj2425scherzerrabar.foundation.ApiKeyFactory;
import spengergasse.at.sj2425scherzerrabar.persistence.converter.BookTypeConverter;

@Entity
@Table(name = "copy")
public class Copy extends BookSpecification {
    @EmbeddedId
    private CopyId copyId;
    @Embedded
    private ApiKey copyApiKey;


    public Copy(Publisher publisher, BookType bookType, Integer pageCount, Book book) {
        super(publisher,bookType,pageCount,book);
        this.copyApiKey = new ApiKeyFactory().generate(30);
        this.publisher = publisher;
        this.bookType = bookType;
        this.pageCount = pageCount;
        this.book = book;
    }

    public Copy() {
        this.copyApiKey = new ApiKeyFactory().generate(30);
    }

    public ApiKey getCopyApiKey() {
        return copyApiKey;
    }

    public void setCopyApiKey(ApiKey copyApiKey) {
        this.copyApiKey = copyApiKey;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Embeddable
    record CopyId (@GeneratedValue @NotNull Long id){}
}
