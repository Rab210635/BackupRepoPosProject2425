package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spengergasse.at.sj2425scherzerrabar.commands.CopyCommand;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.BookType;
import spengergasse.at.sj2425scherzerrabar.domain.Copy;
import spengergasse.at.sj2425scherzerrabar.dtos.CopyDto;
import spengergasse.at.sj2425scherzerrabar.persistence.BookRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.BranchRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.CopyRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.PublisherRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly=true)
public class CopyService {

    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final BranchRepository branchRepository;

    public CopyService(CopyRepository copyRepository, BookRepository bookRepository, PublisherRepository publisherRepository, BranchRepository branchRepository) {
        this.copyRepository = copyRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.branchRepository = branchRepository;
    }

    @Transactional
    public CopyDto createCopy(CopyCommand command) {
        var book = bookRepository.findBookByBookApiKey(command.bookApiKey());
        if(book.isEmpty()) {
            throw new NoSuchElementException("Book not found");
        }
        var publisher = publisherRepository.findPublisherByPublisherApiKey(command.publisherApiKey());
        if(publisher.isEmpty()) {
            throw new NoSuchElementException("Publisher not found");
        }
        var branch = branchRepository.findBranchByBranchApiKey(command.branchApiKey());
        if(branch.isEmpty()) {
            throw new NoSuchElementException("Branch not found");
        }
        Copy copy = new Copy(publisher.get(), command.bookType(),command.pageCount(),book.get(),branch.get());
        copyRepository.save(copy);
        return CopyDto.copyDtoFromCopy(copy);
    }

    @Transactional
    public void deleteCopy(String apiKey) {
        Copy copy = copyRepository.findCopyByCopyApiKey(apiKey)
                .orElseThrow(NoSuchElementException::new);
        copyRepository.delete(copy);
    }

    @Transactional
    public CopyDto updateCopy(CopyCommand command) {
       Copy copy = copyRepository.findCopyByCopyApiKey(command.apiKey()).map((Copy c)->{
            if(!c.getBookType().equals(command.bookType())) {
                c.setBookType(command.bookType());
            }
            if(!c.getPageCount().equals(command.pageCount())) {
                c.setPageCount(command.pageCount());
            }
            if(!c.getBook().getBookApiKey().apiKey().equals(command.bookApiKey())) {
                   bookRepository.findBookByBookApiKey(command.bookApiKey())
                           .ifPresentOrElse(c::setBook,()->{throw new NoSuchElementException("Book not found");} );
            }
            if(!c.getPublisher().getPublisherApiKey().apiKey().equals(command.publisherApiKey())) {
                publisherRepository.findPublisherByPublisherApiKey(command.publisherApiKey())
                        .ifPresentOrElse(c::setPublisher,()->{throw new NoSuchElementException("Publisher not found");} );
            }
            if(!c.getInBranch().getBranchApiKey().apiKey().equals(command.branchApiKey())) {
                branchRepository.findBranchByBranchApiKey(command.branchApiKey())
                        .ifPresentOrElse(c::setInBranch,()->{throw new NoSuchElementException("Branch not found");} );
            }
            copyRepository.save(c);
            return c;
        }).orElseThrow(NoSuchElementException::new);
       return CopyDto.copyDtoFromCopy(copy);
    }

    public CopyDto getCopy(ApiKey apiKey) {
        return copyRepository.getProjectedByCopyApiKey(apiKey.apiKey())
                .orElseThrow(NoSuchElementException::new);
    }

    public List<CopyDto> getCopies() {
        return copyRepository.findAllProjected();
    }

    public List<CopyDto> getCopiesByBook(String bookApiKey) {

        var book = bookRepository.findBookByBookApiKey(bookApiKey);
        if(book.isEmpty()) {
            throw new NoSuchElementException("Book not found");
        }
        return copyRepository.findAllProjectedByBook_BookApiKey(book.get().getBookApiKey().apiKey());
}

    public List<CopyDto> getCopiesByPublisher(String publisherApiKey) {

        var publisher = publisherRepository.findPublisherByPublisherApiKey(publisherApiKey);
        if(publisher.isEmpty()) {
            throw new NoSuchElementException("Publisher not found");
        }
        return copyRepository.findAllProjectedByPublisher_PublisherApiKey(publisher.get().getPublisherApiKey().apiKey());
    }

    public List<CopyDto> getCopiesByBranch(ApiKey branchApiKey) {
        var branch = branchRepository.findBranchByBranchApiKey(branchApiKey.apiKey());
        if(branch.isEmpty()) {
            throw new NoSuchElementException("Branch not found");
        }
        return copyRepository.findAllProjectedByInBranch_BranchApiKey(branch.get().getBranchApiKey().apiKey());
    }

    public List<CopyDto> getCopiesByBookType(String bookType) {
        return copyRepository.findAllProjectedByBookType(BookType.valueOf(bookType));
    }
}
