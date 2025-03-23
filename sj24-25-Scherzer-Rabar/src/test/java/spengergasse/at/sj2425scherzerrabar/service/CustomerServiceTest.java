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
import spengergasse.at.sj2425scherzerrabar.commands.AuthorCommand;
import spengergasse.at.sj2425scherzerrabar.commands.CustomerCommand;
import spengergasse.at.sj2425scherzerrabar.domain.*;
import spengergasse.at.sj2425scherzerrabar.dtos.AuthorDto;
import spengergasse.at.sj2425scherzerrabar.dtos.CustomerDto;
import spengergasse.at.sj2425scherzerrabar.persistence.CustomerRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock private CustomerRepository customerRepository;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        assumeThat(customerRepository).isNotNull();
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void can_create_customer(){
        when(customerRepository.save(any(Customer.class))).then(AdditionalAnswers.returnsFirstArg());

        var customer = customerService.createCustomer( new CustomerCommand(
                new ApiKey("customerApiKey"),List.of(new Address("12","Wien",1212)),
                "Paron", "krabar",new EmailAddress("hoho@sasd.at")));
        assertThat(customer).isNotNull();
    }

    @Test
    void can_delete_existing_customer(){
        Customer customer = FixturesFactory.customer();
        when(customerRepository.findCustomerByCustomerApiKey(any())).thenReturn(Optional.of(customer));

        customerService.deleteCustomer(new ApiKey("validApiKey"));

        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    void cant_delete_not_existing_customer(){
        ApiKey apiKey = new ApiKey("customerApiKey");
        assertThatThrownBy(()->customerService.deleteCustomer(apiKey)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void can_update_existing_customer(){
        Customer customer = FixturesFactory.customer();
        when(customerRepository.findCustomerByCustomerApiKey(any())).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).then(AdditionalAnswers.returnsFirstArg());

        customerService.updateCustomer(new CustomerCommand(
                new ApiKey("customerApiKey"),List.of(new Address("12","Wien",1212)),
                "Mustermann", "Max",new EmailAddress("hoho@sasd.at")));

        verify(customerRepository, times(1)).save(customer);
        assertThat(customer.getLastName()).isEqualTo("Max");
        assertThat(customer.getFirstName()).isEqualTo("Mustermann");
    }

    @Test
    void cant_update_not_existing_customer(){
        assertThatThrownBy(()->customerService.updateCustomer(new CustomerCommand(
                new ApiKey("customerApiKey"),List.of(new Address("12","Wien",1212)),
                "Mustermann", "Max",new EmailAddress("hoho@sasd.at"))))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void can_get_existing_customer(){
        Customer customer = FixturesFactory.customer();
        when(customerRepository.findCustomerByCustomerApiKey(any())).thenReturn(Optional.of(customer));
        var customer1 = customerService.getCustomer(customer.getCustomerApiKey());
        assertThat(customer1).isEqualTo(CustomerDto.customerDtoFromCustomer(customer));
        verify(customerRepository, times(1)).findCustomerByCustomerApiKey(any());
    }

    @Test
    void cant_get_not_existing_customer(){
        assertThatThrownBy(()->customerService.getCustomer(new ApiKey("customerApiKey"))).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void can_get_customers() {
        Customer customer = FixturesFactory.customer();
        Customer customer2 = FixturesFactory.customer();
        when(customerRepository.findAll()).thenReturn(List.of(customer,customer2));

        var customers = customerService.getCustomers();
        assertThat(customers).hasSize(2);
    }

    @Test
    void can_get_existing_customer_by_email_address(){
        Customer customer = FixturesFactory.customer();
        when(customerRepository.getCustomersByEmailAddress_Email(any())).thenReturn(Optional.of(customer));
        var customer1 = customerService.getCustomerByEMail(customer.getEmailAddress().email());
        assertThat(customer1).isNotNull();
        verify(customerRepository, times(1)).getCustomersByEmailAddress_Email(any());
    }

    @Test
    void cant_get_not_existing_customer_by_email_address(){
        assertThatThrownBy(()->customerService.getCustomerByEMail("A")).isInstanceOf(NoSuchElementException.class);
    }
}