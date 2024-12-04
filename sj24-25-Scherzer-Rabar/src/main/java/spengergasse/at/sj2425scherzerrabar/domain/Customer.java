package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import spengergasse.at.sj2425scherzerrabar.persistence.CustomerRepository;

import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends Person {
    @ElementCollection
    @JoinTable(name = "addresses_in_customers", foreignKey = @ForeignKey(name = "FK_customer_addresses"))
    protected List<Address> address;

    public Customer(String firstName, String lastName, Email email, List<Address> address) {
        super(firstName, lastName,address,email);
    }
    public Customer(){
        super();
    }
}
