package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CardDAO;
import by.bsu.rfct.fclearn.entity.Card;
import by.bsu.rfct.fclearn.service.CardService;
import by.bsu.rfct.fclearn.service.dto.card.CardConverter;
import by.bsu.rfct.fclearn.service.dto.card.CardConverterSmall;
import by.bsu.rfct.fclearn.service.dto.card.CardDTO;
import by.bsu.rfct.fclearn.service.util.ServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("cardService")
public class CardServiceImpl implements CardService{

    private static final Logger LOG = LogManager.getLogger(CardServiceImpl.class);

    private CardDAO cardDAO;
    private CardConverter cardConverter;
    private CardConverterSmall cardConverterSmall;

    public CardServiceImpl(CardDAO cardDAO, CardConverter cardConverter, CardConverterSmall cardConverterSmall) {
        this.cardDAO = cardDAO;
        this.cardConverter = cardConverter;
        this.cardConverterSmall = cardConverterSmall;
    }

    @Override
    public CardDTO create(CardDTO dto) {
        return null;
    }

    @Override
    public CardDTO read(Long id) {
        LOG.debug("CardService - read card id={}", id);
        Card card = cardDAO.read(id);
        CardDTO cardDTO = cardConverter.convert(card);
        return cardDTO;
    }

    @Override
    public CardDTO update(CardDTO dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<CardDTO> readAll(Long pageNumber, Long amountOnPage) {
        LOG.debug("CardService - read all");
        throw new UnsupportedOperationException("CardService - read all - unsupported method");
    }

    @Override
    public Long countAll() {
        LOG.debug("CardService - count all");
        return cardDAO.countAll();
    }

    @Override
    public Long countCardAmountInCollection(Long collectionId) {
        LOG.debug("CardService - count card amount in collection id={}", collectionId);
        return cardDAO.countCardAmountInCollection(collectionId);
    }

    @Override
    public List<CardDTO> readAllCardsByCollectionId(Long collectionId, Long pageNumber, Long amountOnPage) {
        LOG.debug("CardService - read all cards by collection id={}", collectionId);
        List<CardDTO> cardDTOs = new ArrayList<>();
        List<Card> cards = cardDAO.readAllCardsByCollectionId(collectionId,
                ServiceUtils.countStartLimitFrom(pageNumber, amountOnPage), amountOnPage);
        for (Card card : cards) {
            CardDTO cardDTO = cardConverterSmall.convert(card);
            cardDTOs.add(cardDTO);
        }
        return cardDTOs;
    }
}
