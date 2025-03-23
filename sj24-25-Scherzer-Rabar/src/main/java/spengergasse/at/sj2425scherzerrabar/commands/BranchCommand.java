package spengergasse.at.sj2425scherzerrabar.commands;

import spengergasse.at.sj2425scherzerrabar.domain.*;

import java.time.LocalDate;
import java.util.List;

public record BranchCommand(ApiKey apiKey,ApiKey libraryApiKey, Address address) {}
