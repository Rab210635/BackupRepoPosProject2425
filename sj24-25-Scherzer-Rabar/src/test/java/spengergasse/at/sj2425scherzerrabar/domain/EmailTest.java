package spengergasse.at.sj2425scherzerrabar.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    public void create_mail_with_valid_values(){
        Email mail = new Email("dasdasdas@gmail.com");
        assertThat(mail).isEqualTo(new Email("dasdasdas@gmail.com"));
    }

    @Test
    public void create_mail_with_null_and_empty_values(){
        assertThatThrownBy(() -> new Email(null)).isInstanceOf(Email.EmailException.class).hasMessageContaining("null");
        assertThatThrownBy(() -> new Email("")).isInstanceOf(Email.EmailException.class).hasMessageContaining("null");
    }

    @Test
    public void create_mail_with_invalid_values(){
        assertThatThrownBy(() -> new Email("teadsad")).isInstanceOf(Email.EmailException.class).hasMessageContaining("invalid");
    }
}