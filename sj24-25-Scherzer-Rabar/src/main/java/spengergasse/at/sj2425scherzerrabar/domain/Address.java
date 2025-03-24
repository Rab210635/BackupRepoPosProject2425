package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record Address (String streetAndNumber, String city, Integer zip) {
    private static final Integer MAX_ZIP = 9999;
    private static final Integer MIN_ZIP = 1010;

    public Address(String streetAndNumber, String city, Integer zip) {

        if(streetAndNumber == null || streetAndNumber.trim().isEmpty()) {
            throw AddressException.forNull();
        }
        this.streetAndNumber = streetAndNumber;
        if (city == null || city.trim().isEmpty()) {
            throw AddressException.forNull();
        }
        this.city = city;
        if(zip == null) {
            throw AddressException.forNull();
        }else if(zip < MIN_ZIP || zip > MAX_ZIP) {
            throw AddressException.forInvalidAddress(streetAndNumber + " " + city + " " + zip);
        }
        this.zip = zip;
    }

    @Override
    public String toString() {
        return streetAndNumber + "-" + city + '-' + zip;
    }

    public static Address addressFromString(String address) {
       var addressAttributes = address.split("-");
       return new Address(addressAttributes[0], addressAttributes[1], Integer.parseInt(addressAttributes[2]));
    }

    public static class AddressException extends RuntimeException {
        public AddressException(String message) {
            super(message);
        }
        static AddressException forNull() {
            final String message = "You have provided a null Value for a Address";
            return new AddressException(message);
        }
        static AddressException forInvalidAddress(String address) {
            final String message = "You have provided an invalid Value for a Address (%s)".formatted(address);
            return new AddressException(message);
        }
    }
}
