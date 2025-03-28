package spengergasse.at.sj2425scherzerrabar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.commands.BorrowingCommand;
import spengergasse.at.sj2425scherzerrabar.domain.*;
import spengergasse.at.sj2425scherzerrabar.dtos.BorrowingDto;
import spengergasse.at.sj2425scherzerrabar.persistence.BorrowingRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.CopyRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.CustomerRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.*;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class BorrowingServiceTest {
    private @Mock BorrowingRepository borrowingRepository;
    private @Mock CustomerRepository customerRepository;
    private @Mock CopyRepository copyRepository;

    private BorrowingService borrowingService;

    @BeforeEach
    void setUp() {
        assumeThat(borrowingRepository).isNotNull();
        assumeThat(customerRepository).isNotNull();
        assumeThat(copyRepository).isNotNull();
        borrowingService = new BorrowingService(borrowingRepository, customerRepository, copyRepository);
    }

    @Test
    void cant_create_borrowing_with_missing_customer() {
        when(customerRepository.findCustomerByCustomerApiKey(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> borrowingService.createBorrowing(
                new BorrowingCommand(new ApiKey("BorrowingApiKey").apiKey(),new ApiKey("invalidCustomer").apiKey(), List.of(new ApiKey("copyApiKey").apiKey()), LocalDate.now(),0)))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void cant_create_borrowing_with_missing_copies() {
        var customer = FixturesFactory.customer();
        when(customerRepository.findCustomerByCustomerApiKey(any())).thenReturn(Optional.of(customer));
        when(copyRepository.findCopyByCopyApiKey(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> borrowingService.createBorrowing(
                new BorrowingCommand(new ApiKey("BorrowingApiKey").apiKey(),new ApiKey("customerApiKey").apiKey(), List.of(new ApiKey("invalidCopy").apiKey()), LocalDate.now(),0)))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void can_create_borrowing() {
        var customer = FixturesFactory.customer();
        var copy = FixturesFactory.copy();

        when(customerRepository.findCustomerByCustomerApiKey(any())).thenReturn(Optional.of(customer));
        when(copyRepository.findCopyByCopyApiKey(any())).thenReturn(Optional.of(copy));
        when(borrowingRepository.save(any(Borrowing.class))).then(AdditionalAnswers.returnsFirstArg());

        BorrowingDto borrowing = borrowingService.createBorrowing(
                new BorrowingCommand(new ApiKey("BorrowingApiKey").apiKey(),new ApiKey("customerApiKey").apiKey(), List.of(new ApiKey("copyApiKey").apiKey()), LocalDate.now(),0));

        assertThat(borrowing).isNotNull();
        assertThat(borrowing.customerApiKey()).isEqualTo(customer.getCustomerApiKey().apiKey());
        assertThat(borrowing.copyApiKeys()).contains(copy.getCopyApiKey().apiKey());
    }

    @Test
    void cant_update_borrowing_with_missing_borrowing() {
        when(borrowingRepository.findBorrowingByBorrowingApiKey(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> borrowingService.updateBorrowing(
                new BorrowingCommand(new ApiKey("BorrowingApiKey").apiKey(), new ApiKey("customerApiKey").apiKey(), List.of(new ApiKey("copyApiKey").apiKey()), LocalDate.now(), 0)))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Borrowing record not found");
    }

    @Test
    void cant_update_borrowing_with_missing_customer() {
        var borrowing = FixturesFactory.borrowing(FixturesFactory.customer(), List.of(FixturesFactory.copy()));
        when(borrowingRepository.findBorrowingByBorrowingApiKey(any())).thenReturn(Optional.of(borrowing));
        when(customerRepository.findCustomerByCustomerApiKey(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> borrowingService.updateBorrowing(
                new BorrowingCommand(new ApiKey("BorrowingApiKey").apiKey(), new ApiKey("invalidCustomerApiKey").apiKey(), List.of(new ApiKey("copyApiKey").apiKey()), LocalDate.now(), 0)))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Customer not found");
    }

    @Test
    void cant_update_borrowing_with_missing_copies() {
        var borrowing = FixturesFactory.borrowing(FixturesFactory.customer(), List.of(FixturesFactory.copy()));
        var customer = FixturesFactory.customer();
        when(borrowingRepository.findBorrowingByBorrowingApiKey(any())).thenReturn(Optional.of(borrowing));
        when(customerRepository.findCustomerByCustomerApiKey(any())).thenReturn(Optional.of(customer));
        when(copyRepository.findCopyByCopyApiKey(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> borrowingService.updateBorrowing(
                new BorrowingCommand(new ApiKey("BorrowingApiKey").apiKey(), new ApiKey("customerApiKey").apiKey(), List.of(new ApiKey("invalidCopy").apiKey()), LocalDate.now(), 0)))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Copies not found");
    }

    @Test
    void can_update_borrowing() {
        var existingBorrowing = FixturesFactory.borrowing(FixturesFactory.customer(), List.of(FixturesFactory.copy()));
        var newCustomer = FixturesFactory.customer();
        var newCopy = FixturesFactory.copy();

        when(borrowingRepository.findBorrowingByBorrowingApiKey(any())).thenReturn(Optional.of(existingBorrowing));
        when(customerRepository.findCustomerByCustomerApiKey(any())).thenReturn(Optional.of(newCustomer));
        when(copyRepository.findCopyByCopyApiKey(any())).thenReturn(Optional.of(newCopy));
        when(borrowingRepository.save(any(Borrowing.class))).then(AdditionalAnswers.returnsFirstArg());

        var updatedBorrowing = borrowingService.updateBorrowing(
                new BorrowingCommand(new ApiKey("BorrowingApiKey").apiKey(), newCustomer.getCustomerApiKey().apiKey(), List.of(newCopy.getCopyApiKey().apiKey()), LocalDate.now(), 0));

        assertThat(updatedBorrowing).isNotNull();
        assertThat(updatedBorrowing.customerApiKey()).isEqualTo(newCustomer.getCustomerApiKey().apiKey());
        assertThat(updatedBorrowing.copyApiKeys()).contains(newCopy.getCopyApiKey().apiKey());
    }

    @Test
    void cant_delete_borrowing_with_missing_borrowing() {
        when(borrowingRepository.findBorrowingByBorrowingApiKey(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> borrowingService.deleteBorrowing(new ApiKey("invalidBorrowingApiKey").apiKey()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Borrowing record not found");
    }

    @Test
    void can_delete_borrowing() {
        var borrowing = FixturesFactory.borrowing(FixturesFactory.customer(), List.of(FixturesFactory.copy()));
        when(borrowingRepository.findBorrowingByBorrowingApiKey(any())).thenReturn(Optional.of(borrowing));

        borrowingService.deleteBorrowing(borrowing.getBorrowingApiKey().apiKey());

        verify(borrowingRepository, times(1)).delete(borrowing);
    }



    @Test
    void can_get_all_borrowings() {
        var borrowing1 = FixturesFactory.borrowing(FixturesFactory.customer(), List.of(FixturesFactory.copy()));
        var borrowing2 = FixturesFactory.borrowing(FixturesFactory.customer(), List.of(FixturesFactory.copy()));

        when(borrowingRepository.findAll()).thenReturn(List.of(borrowing1, borrowing2));

        var borrowings = borrowingService.getAllBorrowings();

        assertThat(borrowings).hasSize(2);
    }

    @Test
    void can_get_borrowings_by_customer() {
        var customer = FixturesFactory.customer();
        var borrowing = FixturesFactory.borrowing(customer, List.of(FixturesFactory.copy()));

        when(customerRepository.findCustomerByCustomerApiKey(any())).thenReturn(Optional.of(customer));
        when(borrowingRepository.findBorrowingsByCustomer(any())).thenReturn(List.of(borrowing));

        var borrowings = borrowingService.getBorrowingsByCustomer(new ApiKey("customerApiKey").apiKey());

        assertThat(borrowings).hasSize(1);
        assertThat(borrowings.get(0).customerApiKey()).isEqualTo(customer.getCustomerApiKey().apiKey());
    }

    @Test
    void cant_get_borrowings_by_non_existing_customer() {
        when(customerRepository.findCustomerByCustomerApiKey(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> borrowingService.getBorrowingsByCustomer(new ApiKey("invalidApiKey").apiKey()))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void can_get_borrowings_by_copy() {
        var copy = FixturesFactory.copy();
        var borrowing = FixturesFactory.borrowing(FixturesFactory.customer(), List.of(copy));

        when(copyRepository.findCopyByCopyApiKey(any())).thenReturn(Optional.of(copy));
        when(borrowingRepository.findBorrowingsByCopiesContaining(any())).thenReturn(List.of(borrowing));

        var borrowings = borrowingService.getBorrowingsByCopy(new ApiKey("copyApiKey").apiKey());

        assertThat(borrowings).hasSize(1);
        assertThat(borrowings.get(0).copyApiKeys()).contains(copy.getCopyApiKey().apiKey());
    }

    @Test
    void cant_get_borrowings_by_non_existing_copy() {
        when(copyRepository.findCopyByCopyApiKey(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> borrowingService.getBorrowingsByCopy(new ApiKey("invalidCopyApiKey").apiKey()))
                .isInstanceOf(NoSuchElementException.class);
    }
}
