package spengergasse.at.sj2425scherzerrabar.dtos;

import spengergasse.at.sj2425scherzerrabar.domain.Address;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.Customer;
import spengergasse.at.sj2425scherzerrabar.domain.EmailAddress;

import java.util.List;
import java.util.stream.Stream;

public record CustomerDto(String apiKey, List<String> addresses, String firstName,
                          String lastName, String emailAddress) {
    public static CustomerDto customerDtoFromCustomer(Customer customer) {
        return new CustomerDto(
          customer.getCustomerApiKey().apiKey(), customer.getAddress().stream().map(Address::toString).toList() ,
                customer.getFirstName(), customer.getLastName(), customer.getEmailAddress().email()
        );
    }
}
