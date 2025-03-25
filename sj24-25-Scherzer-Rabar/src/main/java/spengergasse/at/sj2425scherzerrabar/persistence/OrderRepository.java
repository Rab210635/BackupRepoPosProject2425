package spengergasse.at.sj2425scherzerrabar.persistence;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.Author;
import spengergasse.at.sj2425scherzerrabar.domain.Book;
import spengergasse.at.sj2425scherzerrabar.domain.Order;
import spengergasse.at.sj2425scherzerrabar.dtos.OrderDto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public Optional<Order> findOrderByOrderApiKey(String apiKey);


    List<Order> findAllByCustomer_CustomerApiKey_ApiKey(String customerCustomerApiKeyApiKey);

    List<Order> findAllByDate(@NotNull @PastOrPresent LocalDate date);
}
