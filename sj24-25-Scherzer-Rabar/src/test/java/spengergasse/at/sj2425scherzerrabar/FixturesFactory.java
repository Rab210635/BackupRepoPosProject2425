package spengergasse.at.sj2425scherzerrabar;

import spengergasse.at.sj2425scherzerrabar.domain.*;

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

    public static LibrarySubscription thaliaAll(Library library) {
        return new LibrarySubscription("ThaliaAll",
                "Access to all Online Books of Thalia",
                100.0,library);
    }

    public static Publisher dornbund(Address address) {
        return new Publisher("Dornbund",address);
    }

}
