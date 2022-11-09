package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;

import java.util.List;

public interface CardService {

    public List<Card> getAllCards();

    public Card getCardById(Long id);

    public Card getCardByNumber(String number);

    public void saveCard(Card card);
}
