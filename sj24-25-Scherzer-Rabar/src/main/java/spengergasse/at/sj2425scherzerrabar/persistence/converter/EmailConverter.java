package spengergasse.at.sj2425scherzerrabar.persistence.converter;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import spengergasse.at.sj2425scherzerrabar.domain.Email;

import java.util.Optional;

@Converter(autoApply = true)
public class EmailConverter implements AttributeConverter<Email, String> {

    @Override
    public String convertToDatabaseColumn(Email email) {
        return switch (email){
            default -> email.toString();
        };
    }

    @Override
    public Email convertToEntityAttribute(String s) {
        return switch (s){
            case null -> null;
            default -> new Email(s);
        };
    }
}
