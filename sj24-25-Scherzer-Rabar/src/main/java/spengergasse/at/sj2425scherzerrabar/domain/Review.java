package spengergasse.at.sj2425scherzerrabar.domain;

public record Review (String title, Integer rating, String description, Customer customer, Book book, Branch branch, Publisher publisher) {

}
