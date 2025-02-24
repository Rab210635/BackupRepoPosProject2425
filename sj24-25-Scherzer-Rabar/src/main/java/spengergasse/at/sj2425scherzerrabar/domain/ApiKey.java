package spengergasse.at.sj2425scherzerrabar.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record ApiKey (String apiKey){};
