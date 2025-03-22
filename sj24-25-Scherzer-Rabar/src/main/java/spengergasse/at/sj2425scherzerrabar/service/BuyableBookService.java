package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spengergasse.at.sj2425scherzerrabar.persistence.BuyableBookRepository;

@Service
@Transactional(readOnly=true)
public class BuyableBookService {

    private BuyableBookRepository buyableBookRepository;

    public BuyableBookService(BuyableBookRepository buyableBookRepository) {
        this.buyableBookRepository = buyableBookRepository;
    }
}
