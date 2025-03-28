package spengergasse.at.sj2425scherzerrabar.commands;

import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;

import java.time.LocalDate;
import java.util.List;

public record BorrowingCommand(
        String apiKey, String customerApiKey, List<String> copyApiKeys,
        LocalDate fromDate, Integer extendedByDays) { }