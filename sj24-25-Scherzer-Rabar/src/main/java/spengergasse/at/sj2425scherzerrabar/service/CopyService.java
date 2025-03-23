package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spengergasse.at.sj2425scherzerrabar.commands.BookCommand;
import spengergasse.at.sj2425scherzerrabar.commands.CopyCommand;
import spengergasse.at.sj2425scherzerrabar.commands.PublisherCommand;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.BookType;
import spengergasse.at.sj2425scherzerrabar.domain.Copy;
import spengergasse.at.sj2425scherzerrabar.domain.Publisher;
import spengergasse.at.sj2425scherzerrabar.dtos.CopyDto;
import spengergasse.at.sj2425scherzerrabar.persistence.BookRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.CopyRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.PublisherRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly=true)
public class CopyService {

    private CopyRepository copyRepository;
    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    public CopyService(CopyRepository copyRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.copyRepository = copyRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Transactional
    public CopyDto createCopy(CopyCommand command) {
        var book = bookRepository.findBookByBookApiKey(command.bookApiKey().apiKey());
        if(book.isEmpty()) {
            throw new NoSuchElementException("Book not found");
        }
        var publisher = publisherRepository.findPublisherByPublisherApiKey(command.publisherApiKey().apiKey());
        if(publisher.isEmpty()) {
            throw new NoSuchElementException("Publisher not found");
        }
        Copy copy = new Copy(publisher.get(), command.bookType(),command.pageCount(),book.get());
        copyRepository.save(copy);
        return CopyDto.copyDtoFromCopy(copy);
    }

    @Transactional
    public void deleteCopy(ApiKey apiKey) {
        Copy copy = copyRepository.findCopyByCopyApiKey(apiKey.apiKey())
                .orElseThrow(NoSuchElementException::new);
        copyRepository.delete(copy);
    }

    @Transactional
    public void updateCopy(CopyCommand command) {
        var copy = copyRepository.findCopyByCopyApiKey(command.apiKey().apiKey()).map((Copy c)->{
            if(!c.getBookType().equals(command.bookType())) {
                c.setBookType(command.bookType());
            }
            if(!c.getPageCount().equals(command.pageCount())) {
                c.setPageCount(command.pageCount());
            }
            if(!c.getBook().getBookApiKey().apiKey().equals(command.bookApiKey().apiKey())) {
                   bookRepository.findBookByBookApiKey(command.bookApiKey().apiKey())
                           .ifPresentOrElse(c::setBook,()->{throw new NoSuchElementException("Book not found");} );
            }
            if(!c.getPublisher().getPublisherApiKey().apiKey().equals(command.publisherApiKey().apiKey())) {
                publisherRepository.findPublisherByPublisherApiKey(command.publisherApiKey().apiKey())
                        .ifPresentOrElse(c::setPublisher,()->{throw new NoSuchElementException("Publisher not found");} );
            }
            copyRepository.save(c);
            return c;
        }).orElseThrow(NoSuchElementException::new);
    }

    public CopyDto getCopy(ApiKey apiKey) {
        return copyRepository.findCopyByCopyApiKey(apiKey.apiKey())
                .map(CopyDto::copyDtoFromCopy)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<CopyDto> getCopies() {
        List<Copy> copies = copyRepository.findAll();

        return copies.stream().map(CopyDto::copyDtoFromCopy).collect(Collectors.toList());
    }

    public List<CopyDto> getCopiesByBook(ApiKey bookApiKey) {

        var book = bookRepository.findBookByBookApiKey(bookApiKey.apiKey());
        if(book.isEmpty()) {
            throw new NoSuchElementException("Book not found");
        }
        List<Copy> copies = copyRepository.getCopiesByBook_BookApiKey(book.get().getBookApiKey());

        return copies.stream().map(CopyDto::copyDtoFromCopy).collect(Collectors.toList());
    }

    public List<CopyDto> getCopiesByPublisher(ApiKey publisherApiKey) {

        var publisher = publisherRepository.findPublisherByPublisherApiKey(publisherApiKey.apiKey());
        if(publisher.isEmpty()) {
            throw new NoSuchElementException("Publisher not found");
        }
        List<Copy> copies = copyRepository.getCopiesByPublisher_PublisherApiKey(publisher.get().getPublisherApiKey());

        return copies.stream().map(CopyDto::copyDtoFromCopy).collect(Collectors.toList());
    }

    public List<CopyDto> getCopiesByBookType(BookType bookType) {
        List<Copy> copies = copyRepository.getCopiesByBookType(bookType);

        return copies.stream().map(CopyDto::copyDtoFromCopy).collect(Collectors.toList());
    }
}
