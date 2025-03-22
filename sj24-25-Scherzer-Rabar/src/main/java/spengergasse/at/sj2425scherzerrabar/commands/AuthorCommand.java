package spengergasse.at.sj2425scherzerrabar.commands;

import spengergasse.at.sj2425scherzerrabar.domain.Address;
import spengergasse.at.sj2425scherzerrabar.domain.EmailAddress;

public record AuthorCommand(
        String penname,
        Address address,
        String firstname,
        String lastname,
        EmailAddress emailAddress
)
{}
