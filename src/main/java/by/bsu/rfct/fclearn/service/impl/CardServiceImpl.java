package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CardDAO;
import by.bsu.rfct.fclearn.entity.Card;
import by.bsu.rfct.fclearn.service.CardService;
import by.bsu.rfct.fclearn.dto.card.CardConverter;
import by.bsu.rfct.fclearn.dto.card.CardConverterSmall;
import by.bsu.rfct.fclearn.dto.card.CardDTO;
import by.bsu.rfct.fclearn.dto.card.CardDTOConverter;
import by.bsu.rfct.fclearn.service.exception.EntityExistsException;
import by.bsu.rfct.fclearn.service.util.ServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("cardService")
public class CardServiceImpl implements CardService {

    private static final Logger LOG = LogManager.getLogger(CardServiceImpl.class);

    private CardDAO cardDAO;
    private CardConverter cardConverter;
    private CardConverterSmall cardConverterSmall;
    private CardDTOConverter cardDTOConverter;

    public CardServiceImpl(CardDAO cardDAO, CardConverter cardConverter, CardConverterSmall cardConverterSmall,
                           CardDTOConverter cardDTOConverter) {
        this.cardDAO = cardDAO;
        this.cardConverter = cardConverter;
        this.cardConverterSmall = cardConverterSmall;
        this.cardDTOConverter = cardDTOConverter;
    }

    @Override
    public Long create(CardDTO dto) {
        LOG.debug("CardService - create card question={}", dto.getQuestion());
        if (!cardDAO.checkIfExist(cardDTOConverter.convert(dto))) {
            return cardDAO.create(cardDTOConverter.convert(dto));
        } else {
            throw new EntityExistsException("Such card exists");
        }
    }

    @Override
    public CardDTO read(Long id) {
        LOG.debug("CardService - read card id={}", id);
        Card card = cardDAO.read(id);
        CardDTO cardDTO = cardConverter.convert(card);
        return cardDTO;
    }

    @Override
    public Long update(CardDTO dto) {
        LOG.debug("CardService - update card id={}", dto.getId());
        return cardDAO.update(cardDTOConverter.convert(dto));
    }

    @Override
    public void delete(Long id) {
        LOG.debug("CardService - delete card id={}", id);
        cardDAO.delete(id);
    }

    @Override
    public List<CardDTO> readAll(Integer pageNumber, Integer amountOnPage) {
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
    public List<CardDTO> readAllCardsByCollectionId(Long collectionId, Integer pageNumber, Integer amountOnPage) {
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
