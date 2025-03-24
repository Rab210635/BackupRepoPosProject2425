package spengergasse.at.sj2425scherzerrabar.dtos;


import spengergasse.at.sj2425scherzerrabar.domain.Borrowing;


import java.time.LocalDate;
import java.util.List;

public record BorrowingDto(String apiKey,String customerApiKey, List<String> copyApiKeys,
                           LocalDate fromDate, Integer extendedByDays) {
    public static BorrowingDto borrowingDtoFromBorrowing(Borrowing borrowing) {
        return new BorrowingDto(
          borrowing.getBorrowingApiKey().apiKey(),borrowing.getCustomer().getCustomerApiKey().apiKey(),
          borrowing.getCopies().stream().map(copy -> copy.getCopyApiKey().apiKey()).toList(),
          borrowing.getFromDate(),borrowing.getExtendedByDays()
        );
    }
}