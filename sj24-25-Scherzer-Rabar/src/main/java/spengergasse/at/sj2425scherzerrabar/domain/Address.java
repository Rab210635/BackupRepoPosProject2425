package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record Address (String streetAndNumber, String city, Integer zip) {
}
