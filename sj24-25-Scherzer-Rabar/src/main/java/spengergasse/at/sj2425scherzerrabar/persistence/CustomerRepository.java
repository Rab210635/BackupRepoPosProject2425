package spengergasse.at.sj2425scherzerrabar.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.Customer;
import spengergasse.at.sj2425scherzerrabar.dtos.CustomerDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Optional<Customer> findCustomerByCustomerApiKey(String apiKey);

    Optional<Customer> getCustomersByEmailAddress_Email(String emailAddressEmail);

    @Query("""
    select c from Customer c
        where c.customerApiKey = :apiKey
    """)
    public Optional<CustomerDto> getProjectedCustomerByCustomerApiKey(String apiKey);

    @Query("""
        select c from Customer c
    """)
    public List<CustomerDto> findAllProjected();

    @Query("""
        select c from Customer c
            where c.emailAddress = :emailAddress
    """)
    public Optional<CustomerDto> getProjectedCustomerByEmailAddress_Email(String emailAddressEmail);
}
