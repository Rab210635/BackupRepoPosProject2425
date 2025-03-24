package spengergasse.at.sj2425scherzerrabar.commands;

import spengergasse.at.sj2425scherzerrabar.domain.Address;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.EmailAddress;

import java.util.List;

public record AuthorCommand(
        String apiKey,
        String penname,
        List<String> address,
        String firstname,
        String lastname,
        String emailAddress
)
{}
