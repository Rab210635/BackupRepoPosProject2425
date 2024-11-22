package spengergasse.at.sj2425scherzerrabar.domain;

import java.util.Date;
import java.util.List;

public record Cart (Customer customer, List<Copy> books, List<LibrarySubscription> subscriptions, Date date) {

}
