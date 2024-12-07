package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;

@Embeddable
public class BookInLibraries {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "books_in_libraries_2_book"))
    private Book book;
    private Integer borrowLengthDays;
}
