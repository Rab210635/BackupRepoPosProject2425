package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends Person {
    @ElementCollection
    @JoinTable(name = "addresses_in_customers", foreignKey = @ForeignKey(name = "FK_customer_addresses"))
    protected List<Address> address;

    public Customer(String firstName, String lastName, EmailAddress emailAddress, List<Address> address) {
        super(firstName, lastName,address, emailAddress);
    }
    public Customer(){
        super();
    }
}
