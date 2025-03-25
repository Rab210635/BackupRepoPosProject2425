package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spengergasse.at.sj2425scherzerrabar.commands.BorrowingCommand;
import spengergasse.at.sj2425scherzerrabar.commands.BuyableBookCommand;
import spengergasse.at.sj2425scherzerrabar.commands.OrderCommand;
import spengergasse.at.sj2425scherzerrabar.domain.*;
import spengergasse.at.sj2425scherzerrabar.dtos.BorrowingDto;
import spengergasse.at.sj2425scherzerrabar.dtos.BuyableBookDto;
import spengergasse.at.sj2425scherzerrabar.dtos.OrderDto;
import spengergasse.at.sj2425scherzerrabar.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly=true)
public class OrderService {
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private BuyableBookRepository buyableBookRepository;
    private LibrarySubscriptionRepository librarySubscriptionRepository;

    public OrderService(OrderRepository orderRepository,CustomerRepository CustomerRepository,BuyableBookRepository buyableBookRepository,LibrarySubscriptionRepository librarySubscriptionRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = CustomerRepository;
        this.buyableBookRepository = buyableBookRepository;
        this.librarySubscriptionRepository = librarySubscriptionRepository;
    }

    @Transactional
    public OrderDto createOrder(OrderCommand command) {
        var customer = customerRepository.findCustomerByCustomerApiKey(command.customerApikey());
        if (customer.isEmpty()) {
            throw new NoSuchElementException("Customer not found");
        }
       var subscriptions = command.subscriptionsApiKeys().stream()
                .map(s -> {
                    var subscription =librarySubscriptionRepository.findLibrarySubscriptionByLibrarySubscriptionApiKey(s);
                    if (subscription.isEmpty()) {
                        throw new NoSuchElementException("Library Subscription not found");
                    }
                    return subscription;
                }).flatMap(Optional::stream).toList();

       var buyablebooks = command.booksApiKeys().stream().map(bb-> {
           var buyableBook = buyableBookRepository.findBuyableBookByBuyableBookApiKey(bb);
           if (buyableBook.isEmpty()) {
               throw new NoSuchElementException("Buyable Book not found");
           }
           return buyableBook;
       }).flatMap(Optional::stream).toList();
       Order order = new Order(customer.get(),subscriptions,command.date(),buyablebooks);
       orderRepository.save(order);
       return OrderDto.orderDtoFromOrder(order);
    }

    @Transactional
    public OrderDto updateOrder(OrderCommand command) {
        Order order = orderRepository.findOrderByOrderApiKey(command.apiKey())
                .orElseThrow(()->new NoSuchElementException("Order not found"));

        var customer = customerRepository.findCustomerByCustomerApiKey(command.customerApikey());
        if (customer.isEmpty()) {
            throw new NoSuchElementException("Customer not found");
        }
        var subscriptions = command.subscriptionsApiKeys().stream()
                .map(s -> {
                    var subscription =librarySubscriptionRepository.findLibrarySubscriptionByLibrarySubscriptionApiKey(s);
                    if (subscription.isEmpty()) {
                        throw new NoSuchElementException("Library Subscription not found");
                    }
                    return subscription;
                }).flatMap(Optional::stream).toList();

        var buyablebooks = command.booksApiKeys().stream().map(bb-> {
            var buyableBook = buyableBookRepository.findBuyableBookByBuyableBookApiKey(bb);
            if (buyableBook.isEmpty()) {
                throw new NoSuchElementException("Buyable Book not found");
            }
            return buyableBook;
        }).flatMap(Optional::stream).toList();

        order.setCustomer(customer.get());
        order.setSubscriptions(subscriptions);
        order.setBooks(buyablebooks);
        order.setDate(command.date());

        orderRepository.save(order);
        return OrderDto.orderDtoFromOrder(order);
    }

    @Transactional
    public void deleteOrder(String orderApiKey) {
        Order order = orderRepository.findOrderByOrderApiKey(orderApiKey)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
        orderRepository.delete(order);
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderDto::orderDtoFromOrder)
                .collect(Collectors.toList());
    }

    public OrderDto getOrderByApiKey(String apiKey) {
        Order order = orderRepository.findOrderByOrderApiKey(apiKey)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
        return OrderDto.orderDtoFromOrder(order);
    }

    public List<OrderDto> getAllOrdersByCustomer(String customerApiKey) {
        Customer customer = customerRepository.findCustomerByCustomerApiKey(customerApiKey)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        return orderRepository.findAllByCustomer_CustomerApiKey_ApiKey(customerApiKey).stream()
                .map(OrderDto::orderDtoFromOrder)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getAllOrderByDate(LocalDate date) {
        return orderRepository.findAllByDate(date).stream()
                .map(OrderDto::orderDtoFromOrder)
                .collect(Collectors.toList());
    }
}
