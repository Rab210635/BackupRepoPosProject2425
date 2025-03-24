package spengergasse.at.sj2425scherzerrabar.commands;

import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;

import java.time.LocalDate;
import java.util.List;

public record OrderCommand(
        String apiKey, String customerApikey,
        List<String> subscriptionsApiKeys, LocalDate date, List<String> booksApiKeys
) {}
