package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spengergasse.at.sj2425scherzerrabar.commands.BorrowingCommand;
import spengergasse.at.sj2425scherzerrabar.domain.*;
import spengergasse.at.sj2425scherzerrabar.dtos.BorrowingDto;
import spengergasse.at.sj2425scherzerrabar.persistence.BorrowingRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.CopyRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.CustomerRepository;

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
        Optional<Customer> customer = customerRepository.findCustomerByCustomerApiKey(command.customerApiKey());
        if(customer.isEmpty()) {
            throw new NoSuchElementException("Customer not found");
        }
        List<Copy> copies = command.copyApiKeys().stream()
                .map(copyRepository::findCopyByCopyApiKey)
                .flatMap(Optional::stream)
                .toList();
        if (copies.isEmpty()) {
            throw new NoSuchElementException("Copies not found");
        }
        return BorrowingDto.borrowingDtoFromBorrowing( borrowingRepository.save(new Borrowing(customer.get(),copies, command.fromDate(),0)));
    }


    @Transactional
    public void deleteBorrowing(String borrowingApiKey) {
        Borrowing borrowing = borrowingRepository.findBorrowingByBorrowingApiKey(borrowingApiKey)
                .orElseThrow(() -> new NoSuchElementException("Borrowing record not found"));
        borrowingRepository.delete(borrowing);
    }

    @Transactional
    public BorrowingDto updateBorrowing(BorrowingCommand command) {
        return borrowingRepository.findBorrowingByBorrowingApiKey(command.apiKey())
                .map(borrowing -> {
                    if (!borrowing.getCustomer().getCustomerApiKey().apiKey().equals(command.customerApiKey())) {
                        Customer newCustomer = customerRepository.findCustomerByCustomerApiKey(command.customerApiKey())
                                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
                        borrowing.setCustomer(newCustomer);
                    }

                    List<Copy> newCopies = command.copyApiKeys().stream()
                            .map(copyRepository::findCopyByCopyApiKey)
                            .flatMap(Optional::stream)
                            .toList();

                    if (newCopies.isEmpty()) {
                        throw new NoSuchElementException("Copies not found");
                    }

                    borrowing.setCopies(newCopies);
                    borrowing.setFromDate(command.fromDate());

                    return BorrowingDto.borrowingDtoFromBorrowing( borrowingRepository.save(borrowing));
                }).orElseThrow(() -> new NoSuchElementException("Borrowing record not found"));
    }

    public List<BorrowingDto> getAllBorrowings() {
        return borrowingRepository.findAll().stream().map(BorrowingDto::borrowingDtoFromBorrowing).collect(Collectors.toList());
    }

    public List<BorrowingDto> getBorrowingsByCustomer(String customerApiKey) {
        Customer customer = customerRepository.findCustomerByCustomerApiKey(customerApiKey)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));

        return borrowingRepository.findBorrowingsByCustomer(customer).stream().map(BorrowingDto::borrowingDtoFromBorrowing).collect(Collectors.toList());
    }

    public List<BorrowingDto> getBorrowingsByCopy(String copyApiKey) {
        Copy copy = copyRepository.findCopyByCopyApiKey(copyApiKey)
                .orElseThrow(() -> new NoSuchElementException("Copy not found"));

        return borrowingRepository.findBorrowingsByCopiesContaining(copy).stream().map(BorrowingDto::borrowingDtoFromBorrowing).collect(Collectors.toList());
    }

    public BorrowingDto getBorrowingByApiKey(String borrowingApiKey) {
        Borrowing borrowing = borrowingRepository.findBorrowingByBorrowingApiKey(borrowingApiKey).orElseThrow(() -> new NoSuchElementException("Borrowing record not found"));
        return BorrowingDto.borrowingDtoFromBorrowing(borrowing);
    }

}
