package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CardDAO;
import by.bsu.rfct.fclearn.entity.Card;
import by.bsu.rfct.fclearn.service.CardService;
import by.bsu.rfct.fclearn.service.dto.card.CardDTO;
import by.bsu.rfct.fclearn.service.util.ServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cardService")
public class CardServiceImpl implements CardService{

    private static final Logger LOG = LogManager.getLogger(CardServiceImpl.class);

    @Autowired
    private CardDAO cardDAO;

    @Override
    public CardDTO create(CardDTO dto) {
        return null;
    }

    @Override
    public CardDTO read(Long id) {
        return null;
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
        List<Card> cards = cardDAO.readAll(ServiceUtils.countStartLimitFrom(pageNumber, amountOnPage), amountOnPage);
        return null;
    }

    @Override
    public Long countAll() {
        LOG.debug("CardService - count all");
        return cardDAO.countAll();
    }
}
