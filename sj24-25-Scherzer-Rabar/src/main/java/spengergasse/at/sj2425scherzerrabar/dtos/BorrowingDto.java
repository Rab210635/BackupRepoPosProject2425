package spengergasse.at.sj2425scherzerrabar.dtos;

import spengergasse.at.sj2425scherzerrabar.domain.ApiKey;
import spengergasse.at.sj2425scherzerrabar.domain.Copy;
import spengergasse.at.sj2425scherzerrabar.domain.Customer;

import java.time.LocalDate;
import java.util.List;

public record BorrowingDto(ApiKey apiKey,ApiKey customerApiKey, List<ApiKey> copyApiKeys, LocalDate fromDate, Integer extendedByDays) { }