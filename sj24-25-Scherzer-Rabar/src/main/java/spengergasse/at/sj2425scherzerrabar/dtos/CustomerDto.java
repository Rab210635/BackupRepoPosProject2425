package spengergasse.at.sj2425scherzerrabar.dtos;

import spengergasse.at.sj2425scherzerrabar.domain.Address;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.Customer;
import spengergasse.at.sj2425scherzerrabar.domain.EmailAddress;

import java.util.List;
import java.util.stream.Stream;

public record CustomerDto(ApiKey apiKey, List<Address> addresses, String firstName, String lastName, EmailAddress emailAddress) {
    public static CustomerDto customerDtoFromCustomer(Customer customer) {
        return new CustomerDto(
          customer.getCustomerApiKey(), customer.getAddress(), customer.getFirstName(), customer.getLastName(), customer.getEmailAddress()
        );
    }
}
