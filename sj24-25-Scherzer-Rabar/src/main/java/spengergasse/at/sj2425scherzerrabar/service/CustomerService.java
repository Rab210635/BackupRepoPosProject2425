package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spengergasse.at.sj2425scherzerrabar.commands.CustomerCommand;
import spengergasse.at.sj2425scherzerrabar.domain.Address;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.Customer;
import spengergasse.at.sj2425scherzerrabar.domain.EmailAddress;
import spengergasse.at.sj2425scherzerrabar.dtos.CustomerDto;
import spengergasse.at.sj2425scherzerrabar.persistence.CustomerRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly=true)
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerDto createCustomer(CustomerCommand command) {
        Customer customer = new Customer(
         command.firstName(),command.lastName(),new EmailAddress(command.emailAddress()),
                command.addresses().stream().map(Address::addressFromString).toList()
        );
        customerRepository.save(customer);
        return CustomerDto.customerDtoFromCustomer(customer);
    }

    @Transactional
    public void deleteCustomer(ApiKey apiKey) {
        var customer = customerRepository.findCustomerByCustomerApiKey(apiKey.apiKey())
                .orElseThrow(NoSuchElementException::new);
        customerRepository.delete(customer);
    }

    @Transactional
    public void updateCustomer(CustomerCommand command) {
        customerRepository.findCustomerByCustomerApiKey(command.apiKey()).map((Customer c)->{
            if (!command.firstName().equals(c.getFirstName())) {
                c.setFirstName(command.firstName());
            }
            if (!command.lastName().equals(c.getLastName())) {
                c.setLastName(command.lastName());
            }
            if (!command.emailAddress().equals(c.getEmailAddress().email())) {
                c.setEmailAddress(new EmailAddress(command.emailAddress()));
            }
            c.setAddress(command.addresses().stream().map(Address::addressFromString).toList());

            customerRepository.save(c);
            return c;
        }).orElseThrow(NoSuchElementException::new);
    }

    public List<CustomerDto> getCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(CustomerDto::customerDtoFromCustomer).collect(Collectors.toList());
    }

    public CustomerDto getCustomer(ApiKey apiKey) {
        return customerRepository.findCustomerByCustomerApiKey(apiKey.apiKey()).map(CustomerDto::customerDtoFromCustomer).orElseThrow(NoSuchElementException::new);
    }

    public CustomerDto getCustomerByEMail(String emailAddress) {
        return  customerRepository.getCustomersByEmailAddress_Email(emailAddress)
                .map(CustomerDto::customerDtoFromCustomer)
                .orElseThrow(NoSuchElementException::new);
    }
}
