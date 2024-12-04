package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Email (String email) {
    private static final String EMAIL_REGEX =  "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public Email(String email) {

        if(email == null || email.isEmpty()) {
            throw EmailException.forNull();
        }
        if (!email.matches(EMAIL_REGEX)) {
            throw EmailException.forInvalidMail(email);
        }else {
            this.email = email;
        }

    }

    @Override
    public String toString() {
        return email;
    }

    public static class EmailException extends RuntimeException {
        public EmailException(String message) {
            super(message);
        }
        static Email.EmailException forNull() {
            final String message = "You have provided a null Value for a MailAdress";
            return new EmailException(message);
        }
        static Email.EmailException forInvalidMail(String email) {
            final String message = "You have provided an invalid Value for a MailAdress (%s)".formatted(email);
            return new EmailException(message);
        }
    }
}
