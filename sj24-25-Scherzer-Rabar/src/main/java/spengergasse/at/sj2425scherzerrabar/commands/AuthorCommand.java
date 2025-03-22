package spengergasse.at.sj2425scherzerrabar.commands;

import spengergasse.at.sj2425scherzerrabar.domain.Address;
import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.EmailAddress;

import java.util.List;

public record AuthorCommand(
        ApiKey apiKey,
        String penname,
        List<Address> address,
        String firstname,
        String lastname,
        EmailAddress emailAddress
)
{}
