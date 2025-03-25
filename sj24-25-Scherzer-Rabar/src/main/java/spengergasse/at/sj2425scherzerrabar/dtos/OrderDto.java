package spengergasse.at.sj2425scherzerrabar.dtos;



import spengergasse.at.sj2425scherzerrabar.domain.Order;

import java.time.LocalDate;
import java.util.List;

public record OrderDto(
        String apiKey, String customerApikey,
        List<String> subscriptionsApiKeys, LocalDate date, List<String> booksApiKeys
) {
    public static OrderDto orderDtoFromOrder(Order order) {
       return new OrderDto(order.getOrderApiKey().apiKey(),order.getCustomer().getCustomerApiKey().apiKey(),
                order.getSubscriptions().stream().map(librarySubscription -> librarySubscription.getLibrarySubscriptionApiKey().apiKey()).toList(),
                order.getDate(),order.getBooks().stream().map(buyableBook -> buyableBook.getBuyableBookApiKey().apiKey()).toList()
        );
    }
}
