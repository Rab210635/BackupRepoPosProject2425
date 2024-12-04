package spengergasse.at.sj2425scherzerrabar.persistence.converter;

import org.junit.jupiter.api.Test;
import spengergasse.at.sj2425scherzerrabar.domain.Email;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmailConverterTest {

    @Test
    void convert_valid_mail_to_db() {
        EmailConverter converter = new EmailConverter();
        Email email = new Email("tesxt@gmail.com");
        String convertedValue = converter.convertToDatabaseColumn(email);
        assertThat(convertedValue).isEqualTo("tesxt@gmail.com");
    }

    @Test
    void convert_valid_db_to_mail() {
        String dbValue = "tesxt@gmail.com";
        EmailConverter converter = new EmailConverter();
        Email email = converter.convertToEntityAttribute(dbValue);
        assertThat(email).isEqualTo(new Email("tesxt@gmail.com"));
    }
}