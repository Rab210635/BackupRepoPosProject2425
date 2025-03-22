package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spengergasse.at.sj2425scherzerrabar.commands.BookCommand;
import spengergasse.at.sj2425scherzerrabar.commands.BorrowingCommand;
import spengergasse.at.sj2425scherzerrabar.domain.*;
import spengergasse.at.sj2425scherzerrabar.dtos.BorrowingDto;
import spengergasse.at.sj2425scherzerrabar.persistence.AuthorRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.BorrowingRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.CopyRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.CustomerRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BorrowingService {
    private final BorrowingRepository borrowingRepository;
    private final CustomerRepository customerRepository;
    private final CopyRepository copyRepository;

    public BorrowingService(BorrowingRepository borrowingRepository, CustomerRepository customerRepository, CopyRepository copyRepository) {
        this.borrowingRepository = borrowingRepository;
        this.customerRepository = customerRepository;
        this.copyRepository = copyRepository;
    }

    @Transactional
    public BorrowingDto createBorrowing(BorrowingCommand command) {
        Optional<Customer> customer = customerRepository.findCustomerByCustomerApiKey(command.customerApiKey().apiKey());
        if(customer.isEmpty()) {
            throw new NoSuchElementException("Customer not found");
        }
        List<Copy> copies = command.copyApiKeys().stream()
                .map(Record::toString)
                .map(copyRepository::findCopyByCopyApiKey)
                .flatMap(Optional::stream)
                .toList();
        if (copies.isEmpty()) {
            throw new NoSuchElementException("Copies not found");
        }
        return toDto( borrowingRepository.save(new Borrowing(customer.get(),copies, command.fromDate(),0)));
    }


    @Transactional
    public void deleteBorrowing(ApiKey borrowingApiKey) {
        Borrowing borrowing = borrowingRepository.findBorrowingByBorrowingApiKey(borrowingApiKey.apiKey())
                .orElseThrow(() -> new NoSuchElementException("Borrowing record not found"));
        borrowingRepository.delete(borrowing);
    }

    @Transactional
    public BorrowingDto updateBorrowing(BorrowingCommand command) {
        return borrowingRepository.findBorrowingByBorrowingApiKey(command.apiKey().apiKey())
                .map(borrowing -> {
                    if (!borrowing.getCustomer().getCustomerApiKey().equals(command.customerApiKey().apiKey())) {
                        Customer newCustomer = customerRepository.findCustomerByCustomerApiKey(command.customerApiKey().apiKey())
                                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
                        borrowing.setCustomer(newCustomer);
                    }

                    List<Copy> newCopies = command.copyApiKeys().stream()
                            .map(Record::toString)
                            .map(copyRepository::findCopyByCopyApiKey)
                            .flatMap(Optional::stream)
                            .toList();

                    if (newCopies.isEmpty()) {
                        throw new NoSuchElementException("Copies not found");
                    }

                    borrowing.setCopies(newCopies);
                    borrowing.setFromDate(command.fromDate());

                    return toDto( borrowingRepository.save(borrowing));
                }).orElseThrow(() -> new NoSuchElementException("Borrowing record not found"));
    }

    public List<BorrowingDto> getAllBorrowings() {
        return borrowingRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<BorrowingDto> getBorrowingsByCustomer(ApiKey customerApiKey) {
        Customer customer = customerRepository.findCustomerByCustomerApiKey(customerApiKey.apiKey())
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));

        return borrowingRepository.findBorrowingsByCustomer(customer).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<BorrowingDto> getBorrowingsByCopy(ApiKey copyApiKey) {
        Copy copy = copyRepository.findCopyByCopyApiKey(copyApiKey.apiKey())
                .orElseThrow(() -> new NoSuchElementException("Copy not found"));

        return borrowingRepository.findBorrowingsByCopiesContaining(copy).stream().map(this::toDto).collect(Collectors.toList());
    }

    public BorrowingDto getBorrowingByApiKey(ApiKey borrowingApiKey) {
        Borrowing borrowing = borrowingRepository.findBorrowingByBorrowingApiKey(borrowingApiKey.apiKey()).orElseThrow(() -> new NoSuchElementException("Borrowing record not found"));
        return toDto(borrowing);
    }

    private BorrowingDto toDto(Borrowing borrowing) {
        return new BorrowingDto(
                borrowing.getBorrowingApiKey(),
                borrowing.getCustomer().getCustomerApiKey(),
                borrowing.getCopies().stream().map(Copy::getCopyApiKey).collect(Collectors.toList()),
                borrowing.getFromDate(),
                borrowing.getExtendedByDays()
        );
    }

}
