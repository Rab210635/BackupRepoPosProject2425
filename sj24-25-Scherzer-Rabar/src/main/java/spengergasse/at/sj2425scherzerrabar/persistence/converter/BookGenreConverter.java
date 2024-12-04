package spengergasse.at.sj2425scherzerrabar.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import spengergasse.at.sj2425scherzerrabar.domain.BookGenre;

@Converter(autoApply = true)
public class BookGenreConverter implements AttributeConverter<BookGenre, String> {

    static final String VALID_VALUES = "'MI','TH','CR','RO','FA','SF','HF','CF','YA','BI','AU','ME','SH','TC','HI','SC','TE','PH','RE','SP','GN','CO','PO','HO'";
    public static final String COLUMN_DEFINITION = "enum (" + VALID_VALUES + ")";

    @Override
    public String convertToDatabaseColumn(BookGenre bookGenre) {
        if (bookGenre == null) return null;

        switch (bookGenre) {
            case MYSTERY: return "MY";
            case THRILLER: return "TH";
            case CRIME: return "CR";
            case ROMANCE: return "RO";
            case FANTASY: return "FA";
            case SCIENCE_FICTION: return "SF";
            case HISTORICAL_FICTION: return "HF";
            case CONTEMPORARY_FICTION: return "CF";
            case YOUNG_ADULT: return "YA";
            case BIOGRAPHY: return "BI";
            case AUTOBIOGRAPHY: return "AU";
            case MEMOIR: return "ME";
            case SELF_HELP: return "SH";
            case TRUE_CRIME: return "TC";
            case HISTORY: return "HI";
            case SCIENCE: return "SC";
            case TECHNOLOGY: return "TE";
            case PHILOSOPHY: return "PH";
            case RELIGION: return "RE";
            case SPIRITUALITY: return "SP";
            case GRAPHIC_NOVELS: return "GN";
            case COMICS: return "CO";
            case POETRY: return "PO";
            case HORROR: return "HO";
            default: throw new IllegalArgumentException("Unknown BookGenre: " + bookGenre);
        }
    }

    @Override
    public BookGenre convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) return null;

        switch (s.toUpperCase()) {
            case "MY": return BookGenre.MYSTERY;
            case "TH": return BookGenre.THRILLER;
            case "CR": return BookGenre.CRIME;
            case "RO": return BookGenre.ROMANCE;
            case "FA": return BookGenre.FANTASY;
            case "SF": return BookGenre.SCIENCE_FICTION;
            case "HF": return BookGenre.HISTORICAL_FICTION;
            case "CF": return BookGenre.CONTEMPORARY_FICTION;
            case "YA": return BookGenre.YOUNG_ADULT;
            case "BI": return BookGenre.BIOGRAPHY;
            case "AU": return BookGenre.AUTOBIOGRAPHY;
            case "ME": return BookGenre.MEMOIR;
            case "SH": return BookGenre.SELF_HELP;
            case "TC": return BookGenre.TRUE_CRIME;
            case "HI": return BookGenre.HISTORY;
            case "SC": return BookGenre.SCIENCE;
            case "TE": return BookGenre.TECHNOLOGY;
            case "PH": return BookGenre.PHILOSOPHY;
            case "RE": return BookGenre.RELIGION;
            case "SP": return BookGenre.SPIRITUALITY;
            case "GN": return BookGenre.GRAPHIC_NOVELS;
            case "CO": return BookGenre.COMICS;
            case "PO": return BookGenre.POETRY;
            case "HO": return BookGenre.HORROR;
            default: throw BookGenreException.withInvalidDatabaseValue(s);
        }
    }
    public static class BookGenreException extends RuntimeException {
        public BookGenreException(String message) {
            super(message);
        }
        public static BookGenreException withInvalidDatabaseValue(String value){
            String message = "The value provided is not valid: (%s)".formatted(value);
            return new BookGenreException(message);
        }
    }

}
