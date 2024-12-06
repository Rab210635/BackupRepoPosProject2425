package spengergasse.at.sj2425scherzerrabar.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import spengergasse.at.sj2425scherzerrabar.domain.BookType;

@Converter(autoApply = true)
public class BookTypeConverter implements AttributeConverter<BookType, Character> {
    static final String VALID_VALUES = "'H','P','E'";
    //TODO
    public static final String COLUMN_DEFINITION = "enum ( "+ VALID_VALUES+")";
    
    @Override
    public Character convertToDatabaseColumn(BookType bookType) {

        return switch (bookType) {
            case HARDCOVER -> 'H';
            case PAPERBACK -> 'P';
            case EBOOK -> 'E';
            case null -> throw new NullPointerException("BookType is null");
            default -> throw new IllegalArgumentException("Unsupported BookType: " + bookType);
        };
    }

    @Override
    public BookType convertToEntityAttribute(Character c) {
        return switch (c) {
            case 'H', 'h' -> BookType.HARDCOVER;
            case 'P', 'p' -> BookType.PAPERBACK;
            case 'E', 'e' -> BookType.EBOOK;
            case null -> throw new NullPointerException("BookType is null");
            default -> throw BookTypeException.withInvalidDatabaseValue(c);
        };
    }
    public static class BookTypeException extends RuntimeException {
        public BookTypeException(String message) {
            super(message);
        }
        public static BookTypeException withInvalidDatabaseValue(Character c) {
            String message = "The value provided is not valid: (%c)".formatted(c);
            return new BookTypeException(message);
        }

    }
}
