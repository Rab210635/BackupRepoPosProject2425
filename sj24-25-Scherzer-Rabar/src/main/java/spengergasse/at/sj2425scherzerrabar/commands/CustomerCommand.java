package spengergasse.at.sj2425scherzerrabar.commands;

import spengergasse.at.sj2425scherzerrabar.domain.Address;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.EmailAddress;

import java.util.List;

public record CustomerCommand(
        String apiKey, List<String> addresses, String firstName,
        String lastName, String emailAddress) {
}
