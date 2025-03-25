package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spengergasse.at.sj2425scherzerrabar.commands.BranchCommand;
import spengergasse.at.sj2425scherzerrabar.commands.BuyableBookCommand;
import spengergasse.at.sj2425scherzerrabar.domain.*;
import spengergasse.at.sj2425scherzerrabar.dtos.BranchDto;
import spengergasse.at.sj2425scherzerrabar.dtos.BuyableBookDto;
import spengergasse.at.sj2425scherzerrabar.dtos.PublisherDto;
import spengergasse.at.sj2425scherzerrabar.persistence.BookRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.BuyableBookRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.LibraryRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.PublisherRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly=true)
public class BuyableBookService {

    private BuyableBookRepository buyableBookRepository;
    private PublisherRepository publisherRepository;
    private BookRepository bookRepository;

    public BuyableBookService(BuyableBookRepository buyableBookRepository, PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.buyableBookRepository = buyableBookRepository;
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public BuyableBookDto createBuyableBook(BuyableBookCommand command) {
        var book = bookRepository.findBookByBookApiKey(command.bookApiKey());
        if (book.isEmpty()) {
            throw new NoSuchElementException("Book not found");
        }
        var publisher = publisherRepository.findPublisherByPublisherApiKey(command.publisherApiKey());

        if (publisher.isEmpty()) {
            throw new NoSuchElementException("Publisher not found");
        }

        return BuyableBookDto.buyableBookDtoFromBuyableBook(
                buyableBookRepository.save(new BuyableBook(publisher.get(), BookType.valueOf(command.bookType()), command.pageCount(),book.get(),command.price())));
    }

    @Transactional
    public BuyableBookDto updateBuyableBook(BuyableBookCommand command) {
        BuyableBook buyableBook = buyableBookRepository.findBuyableBookByBuyableBookApiKey(command.buyableBookApiKey())
                .orElseThrow(() -> new NoSuchElementException("Buyable Book not found"));

        var book = bookRepository.findBookByBookApiKey(command.bookApiKey())
                .orElseThrow(() -> new NoSuchElementException("Book not found"));

        var publisher = publisherRepository.findPublisherByPublisherApiKey(command.publisherApiKey())
                .orElseThrow(() -> new NoSuchElementException("Publisher not found"));


        buyableBook.setPublisher(publisher);
        buyableBook.setPageCount(command.pageCount());
        buyableBook.setPrice(command.price());
        buyableBook.setBook(book);
        buyableBook.setBookType(BookType.valueOf(command.bookType()));

        buyableBookRepository.save(buyableBook);
        return BuyableBookDto.buyableBookDtoFromBuyableBook(buyableBook);
    }

    @Transactional
    public void deleteBuyableBook(String buyableBookApiKey) {
        BuyableBook buyableBook = buyableBookRepository.findBuyableBookByBuyableBookApiKey(buyableBookApiKey)
                .orElseThrow(() -> new NoSuchElementException("Buyable Book not found"));
        buyableBookRepository.delete(buyableBook);
    }

    public List<BuyableBookDto> getAllBuyableBooks() {
        return buyableBookRepository.findAll().stream()
                .map(BuyableBookDto::buyableBookDtoFromBuyableBook)
                .collect(Collectors.toList());
    }

    public BuyableBookDto getBuyableBookByApiKey(String apiKey) {
        BuyableBook buyableBook = buyableBookRepository.findBuyableBookByBuyableBookApiKey(apiKey)
                .orElseThrow(() -> new NoSuchElementException("Buyable Book not found"));
        return BuyableBookDto.buyableBookDtoFromBuyableBook(buyableBook);
    }

    //TODO get List with price, book, booktype, publisher
}
