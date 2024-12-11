package spengergasse.at.sj2425scherzerrabar;

import spengergasse.at.sj2425scherzerrabar.domain.*;

import java.util.Date;
import java.util.List;

public class FixturesFactory {

    public static Address libraryAddress() {
        return new Address("spengergasse 20","Vienna",1050);
    }
    public static Address address2() {
        return new Address("Reumanplatz 66","Vienna",1100);
    }

    public static Library thalia(Address address, List<BookInLibraries> books) {
        return new Library("Thalia",address, books);
    }

    public static Author author(Address address, EmailAddress email) {
        return new Author("Max","Mustermann",List.of(address), email, "dada");
    }

    public static Book book(Author author){
        return new Book("dasd",new Date(Date.UTC(5,5,5,0,0,0)),true,1250,List.of(BookGenre.ROMANCE),List.of(author),List.of(BookType.EBOOK));

    }

    public static BookInLibraries libBook(Book b){
        return new BookInLibraries(b,31);
    }

    public static LibrarySubscription thaliaAll(Library library) {
        return new LibrarySubscription("ThaliaAll",
                "Access to all Online Books of Thalia",
                100.0,library);
    }

    public static Publisher dornbund(Address address) {
        return new Publisher("Dornbund",address);
    }

    public static Branch filiale(Address address, List<BookInLibraries> books) {
        return new Branch(thalia(address2(),books),address);
    }
}
