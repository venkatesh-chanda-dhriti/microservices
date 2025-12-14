package com.eazybytes.cards.service.impl;

import com.eazybytes.cards.constant.CardsConstants;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Card;
import com.eazybytes.cards.exception.CardAlreadyExistsException;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.mapper.CardMapper;
import com.eazybytes.cards.repository.CardsRepository;
import com.eazybytes.cards.service.ICardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class CardsService  implements ICardsService {

    @Autowired
    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {

        Optional<Card> optionalCard = cardsRepository.findByMobileNumber(mobileNumber);

        if(optionalCard.isPresent())
                throw(new CardAlreadyExistsException("Card already registered with given mobileNumber: "+mobileNumber));

        cardsRepository.save(createNewCard(mobileNumber));
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {

       Card card = cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "MobileNumber", mobileNumber));

       CardsDto cardsDto = CardMapper.mapToCardToCardDto(card, new CardsDto());

       return cardsDto;
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {

        Card card = cardsRepository.findByMobileNumber(cardsDto.getMobileNumber())
                .orElseThrow(() ->new ResourceNotFoundException("Card", "MobileNumber", cardsDto.getMobileNumber()));

        CardMapper.mapToCardDtoToCard(cardsDto, card);
        cardsRepository.save(card);

        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {

        Card card = cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->new ResourceNotFoundException("Card", "MobileNumber", mobileNumber));

        cardsRepository.deleteById(card.getCardId());

        return true;
    }

    private Card createNewCard(String mobileNumber) {

        Card card = new Card();
        Long cardNumber = 1000000000L+ new Random().nextInt(900000000);

        card.setCardNumber(Long.toString(cardNumber));
        card.setCardType(CardsConstants.CREDIT_CARD);
        card.setAmountUsed(0);
        card.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        card.setMobileNumber(mobileNumber);
        card.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);

        return card;
    }
}
