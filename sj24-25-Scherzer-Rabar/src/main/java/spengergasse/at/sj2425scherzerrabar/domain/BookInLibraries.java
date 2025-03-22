package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Objects;

@Data
@Embeddable
public class BookInLibraries {
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "books_in_libraries_2_book"))
    private Book book;
    @NotNull
    @Min(0)
    @Max(365)
    private Integer borrowLengthDays;

    public BookInLibraries(Book book, Integer borrowLengthDays) {
        this.book = book;
        this.borrowLengthDays = borrowLengthDays;
    }
    public  BookInLibraries(){

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookInLibraries that = (BookInLibraries) o;
        return Objects.equals(book, that.book) && Objects.equals(borrowLengthDays, that.borrowLengthDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, borrowLengthDays);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getBorrowLengthDays() {
        return borrowLengthDays;
    }

    public void setBorrowLengthDays(Integer borrowLengthDays) {
        this.borrowLengthDays = borrowLengthDays;
    }
}
